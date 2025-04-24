package com.estefaniacastro.nurseryAPI.domain.port.in;

import com.estefaniacastro.nurseryAPI.application.dto.ChildDTO;

import java.util.List;
import java.util.Optional;

public interface ChildService {
    List<ChildDTO> findAll();
    Optional<ChildDTO> findById(Long id);
    ChildDTO save(ChildDTO childDTO);
    void deleteById(Long id);
}
