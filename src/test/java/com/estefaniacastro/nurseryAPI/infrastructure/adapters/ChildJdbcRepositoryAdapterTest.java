package com.estefaniacastro.nurseryAPI.infrastructure.adapters;

import com.estefaniacastro.nurseryAPI.domain.model.Child;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ChildJdbcRepositoryAdapterTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    private ChildJdbcRepositoryAdapter childJdbcRepositoryAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        childJdbcRepositoryAdapter = new ChildJdbcRepositoryAdapter(jdbcTemplate);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Child expectedChild = new Child(id, "John", "Doe", LocalDate.of(2022, 3, 5));
        when(jdbcTemplate.query(eq("SELECT * FROM children WHERE id = ?"), any(RowMapper.class), eq(id)))
                .thenReturn(List.of(expectedChild));

        Optional<Child> result = childJdbcRepositoryAdapter.findById(id);

        assertTrue(result.isPresent(), "Expected a child to be found.");
        assertEquals(expectedChild, result.get(), "Expected the returned child to be the same.");
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM children WHERE id = ?"),
                any(RowMapper.class),
                eq(id));  // Ensure the row mapper and id are used correctly
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 99L;
        when(jdbcTemplate.query(eq("SELECT * FROM children WHERE id = ?"), any(RowMapper.class), eq(id)))
                .thenReturn(List.of());

        Optional<Child> result = childJdbcRepositoryAdapter.findById(id);

        assertFalse(result.isPresent(), "Expected no child to be found.");
        verify(jdbcTemplate, times(1)).query(eq("SELECT * FROM children WHERE id = ?"),
                any(RowMapper.class),
                eq(id));  // Ensure the row mapper and id are used correctly
    }

    @Test
    public void testSave() {
        Long generatedId = 1L;
        Child child = new Child(null, "John", "Doe", LocalDate.of(2022, 3, 5));
//        String insertSql = "INSERT INTO children (id, first_name, last_name, birth_date) VALUES (?, ?, ?, ?)";

        doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            Map<String, Object> keys = new HashMap<>();
            keys.put("id", generatedId);
            keyHolder.getKeyList().add(keys);
            return 1;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        Child result = childJdbcRepositoryAdapter.save(child);

        assertThat(result.id()).isEqualTo(generatedId);
        assertThat(result.firstName()).isEqualTo("John");
        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        String deleteSql = "DELETE FROM children WHERE id = ?";
        when(jdbcTemplate.update(eq(deleteSql), eq(id))).thenReturn(1);

        childJdbcRepositoryAdapter.deleteById(id);

        verify(jdbcTemplate, times(1)).update(eq(deleteSql), eq(id));
    }
}
