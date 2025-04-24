package com.estefaniacastro.nurseryAPI.application.mapper;

import com.estefaniacastro.nurseryAPI.application.dto.ChildDTO;
import com.estefaniacastro.nurseryAPI.domain.model.Child;

public class ChildMapper {
    public static ChildDTO toDTO(Child child) {
        return new ChildDTO(child.id(), child.firstName(), child.lastName(), child.birthDate());
    }

    public static Child toDomain(ChildDTO dto) {
        return new Child(dto.id(), dto.firstName(), dto.lastName(), dto.birthDate());
    }
}
