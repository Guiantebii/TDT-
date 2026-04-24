package com.example.tablets_chips.controller;

import com.example.tablets_chips.dto.ChipRequestDTO;
import com.example.tablets_chips.dto.ChipResponseDTO;
import com.example.tablets_chips.service.ChipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chips")
public class ChipController {

    private final ChipService chipService;

    public ChipController(ChipService chipService) {
        this.chipService = chipService;
    }

    @GetMapping
    public ResponseEntity<List<ChipResponseDTO>> listar() {
        return ResponseEntity.ok(chipService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChipResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(chipService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ChipResponseDTO> criar(@Valid @RequestBody ChipRequestDTO dto) {
        return ResponseEntity.ok(chipService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChipResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ChipRequestDTO dto
    ) {
        return ResponseEntity.ok(chipService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        chipService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}