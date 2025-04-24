package com.estefaniacastro.nurseryAPI.application.dto;

import com.estefaniacastro.nurseryAPI.infrastructure.serialization.FlexibleLocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Data Transfer Object for transferring child data.
 * This DTO is used to decouple the domain model from external layers.
 */
public record ChildDTO(
        Long id,

        @NotBlank(message = "Must not be blank")
        String firstName,

        @NotBlank(message = "Must not be blank")
        String lastName,

        @JsonDeserialize(using = FlexibleLocalDateDeserializer.class)
        @NotNull(message = "Must not be null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {}
