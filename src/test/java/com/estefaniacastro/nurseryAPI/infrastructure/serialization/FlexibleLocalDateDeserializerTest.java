package com.estefaniacastro.nurseryAPI.infrastructure.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlexibleLocalDateDeserializerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FlexibleLocalDateDeserializerTest() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new FlexibleLocalDateDeserializer());
        objectMapper.registerModule(module);
    }

    @Test
    void testDeserializeDateWithSlashSeparator() throws IOException {
        String json = "\"2022/03/05\"";
        LocalDate expectedDate = LocalDate.of(2022, 3, 5);

        LocalDate actualDate = objectMapper.readValue(json, LocalDate.class);

        assertEquals(expectedDate, actualDate, "The deserialized date should match the expected date.");
    }

    @Test
    void testDeserializeDateWithDashSeparator() throws IOException {
        String json = "\"2022-03-05\"";
        LocalDate expectedDate = LocalDate.of(2022, 3, 5);

        LocalDate actualDate = objectMapper.readValue(json, LocalDate.class);

        assertEquals(expectedDate, actualDate, "The deserialized date should match the expected date.");
    }

    @Test
    void testDeserializeDateWithDifferentFormat() throws IOException {
        String json = "\"05-03-2022\"";
        LocalDate expectedDate = LocalDate.of(2022, 3, 5);

        LocalDate actualDate = objectMapper.readValue(json, LocalDate.class);

        assertEquals(expectedDate, actualDate, "The deserialized date should match the expected date.");
    }

    @Test
    void testDeserializeInvalidDate() {
        String json = "\"invalid-date\"";

        try {
            objectMapper.readValue(json, LocalDate.class);
        } catch (IOException e) {
            assertEquals("Invalid date format for value: invalid-date", e.getMessage().substring(54, 97), "The error message should indicate a deserialization failure.");
        }
    }
}
