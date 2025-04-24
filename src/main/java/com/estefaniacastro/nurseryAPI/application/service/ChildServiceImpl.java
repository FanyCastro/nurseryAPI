package com.estefaniacastro.nurseryAPI.application.service;

import com.estefaniacastro.nurseryAPI.application.dto.ChildDTO;
import com.estefaniacastro.nurseryAPI.application.mapper.ChildMapper;
import com.estefaniacastro.nurseryAPI.domain.model.Child;
import com.estefaniacastro.nurseryAPI.domain.port.in.ChildService;
import com.estefaniacastro.nurseryAPI.domain.port.out.ChildRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChildServiceImpl implements ChildService {

    private final ChildRepository childRepository;

    public ChildServiceImpl(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Override
    public List<ChildDTO> findAll() {
        return childRepository.findAll().stream()
                .map(ChildMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ChildDTO> findById(Long id) {
        return childRepository.findById(id)
                .map(ChildMapper::toDTO);
    }

    @Override
    public ChildDTO save(ChildDTO childDTO) {
        Child child = ChildMapper.toDomain(childDTO);
        return ChildMapper.toDTO(childRepository.save(child));
    }

    @Override
    public void deleteById(Long id) {
        childRepository.deleteById(id);
    }
}
