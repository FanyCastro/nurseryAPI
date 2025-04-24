package com.estefaniacastro.nurseryAPI.domain.port.out;

import com.estefaniacastro.nurseryAPI.domain.model.Child;

import java.util.List;
import java.util.Optional;

public interface ChildRepository {
    List<Child> findAll();
    Optional<Child> findById(Long id);
    Child save(Child child);
    void deleteById(Long id);
}
