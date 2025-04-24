package com.estefaniacastro.nurseryAPI.domain.model;

import java.time.LocalDate;

public record Child(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate
) {
    public Child {
        if (isBlank(firstName)) {
            throw new IllegalArgumentException("First name must not be blank");
        }
        if (isBlank(lastName)) {
            throw new IllegalArgumentException("Last name must not be blank");
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }
    }

    public int age() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
