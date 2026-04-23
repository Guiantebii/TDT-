package com.example.tablets_chips.controller;

import com.example.tablets_chips.dto.TabletRequestDTO;
import com.example.tablets_chips.dto.TabletResponseDTO;
import com.example.tablets_chips.service.TabletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tablets")
public class TabletController {

    private final TabletService tabletService;

    public TabletController(TabletService tabletService) {
        this.tabletService = tabletService;
    }

    @GetMapping
    public ResponseEntity<List<TabletResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tabletService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TabletResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tabletService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TabletResponseDTO> criar(@Valid @RequestBody TabletRequestDTO dto) {
        return ResponseEntity.ok(tabletService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TabletResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody TabletRequestDTO dto
    ) {
        return ResponseEntity.ok(tabletService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        tabletService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}