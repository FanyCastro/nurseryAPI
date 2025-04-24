package com.estefaniacastro.nurseryAPI.infrastructure.web;

import com.estefaniacastro.nurseryAPI.application.dto.ChildDTO;
import com.estefaniacastro.nurseryAPI.domain.port.in.ChildService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChildController.class)
@Import(GlobalExceptionHandler.class)
public class ChildControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChildService childService;

    @Test
    void shouldReturnAllChildren() throws Exception {
        List<ChildDTO> children = List.of(
                new ChildDTO(1L, "Luna", "Castro", LocalDate.of(2020, 3, 5)),
                new ChildDTO(2L, "Max", "Castro", LocalDate.of(2022, 5, 10))
        );

        when(childService.findAll()).thenReturn(children);

        mockMvc.perform(get("/api/v1/children")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Luna"))
                .andExpect(jsonPath("$[1].firstName").value("Max"));
    }

    @Test
    void shouldReturnChildById() throws Exception {
        ChildDTO childDTO = new ChildDTO(1L, "Luna", "Castro", LocalDate.of(2020, 3, 5));

        when(childService.findById(eq(1L))).thenReturn(Optional.of(childDTO));

        mockMvc.perform(get("/api/v1/children/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Luna"))
                .andExpect(jsonPath("$.lastName").value("Castro"));
    }

    @Test
    void shouldReturnNotFoundWhenChildDoesNotExist() throws Exception {
        when(childService.findById(eq(999L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/children/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateChildWithInvalidData_thenReturn400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/children")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\":\"Doe\",\"birthDate\":\"2000-01-01\"}")) // Missing firstName
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0].field").value("firstName"))
                .andExpect(jsonPath("$.errors[0].message").value("Must not be blank"))
                .andReturn();
    }

    @Test
    public void whenCreateChildWithValidData_thenReturn201Created() throws Exception {
        ChildDTO validChildDTO = new ChildDTO(1L, "John", "Doe", LocalDate.of(2020, 3, 5));

        when(childService.save(Mockito.any(ChildDTO.class))).thenReturn(validChildDTO);

        mockMvc.perform(post("/api/v1/children")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"2020-03-05\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.birthDate").value("2020-03-05"));
    }

    @Test
    void shouldDeleteChild() throws Exception {
        Long id = 1L;

        Mockito.doNothing().when(childService).deleteById(eq(id));

        mockMvc.perform(delete("/api/v1/children/1"))
                .andExpect(status().isNoContent());
    }
}
