package com.estefaniacastro.nurseryAPI.infrastructure.web;
import com.estefaniacastro.nurseryAPI.application.dto.ChildDTO;
import com.estefaniacastro.nurseryAPI.domain.port.in.ChildService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/children")
public class ChildController {

    private final ChildService childService;

    @Autowired
    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping
    public ResponseEntity<List<ChildDTO>> getAllChildren() {
        return ResponseEntity.ok().body(childService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildDTO> getChildById(@PathVariable Long id) {
        return childService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChildDTO> createChild(@Valid @RequestBody  ChildDTO childDTO) {
        ChildDTO createdChild = childService.save(childDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdChild.id())
                .toUri();

        return ResponseEntity.created(location).body(createdChild);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        childService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}