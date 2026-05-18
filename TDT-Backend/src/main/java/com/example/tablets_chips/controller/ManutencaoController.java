package com.example.tablets_chips.controller;


import com.example.tablets_chips.dto.ManutencaoRequestDTO;
import com.example.tablets_chips.dto.ManutencaoResponseDTO;
import com.example.tablets_chips.service.ManutencaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manutencoes")
public class ManutencaoController {
    private final ManutencaoService manutencaoService;

    public ManutencaoController(ManutencaoService manutencaoService) {
        this.manutencaoService = manutencaoService;
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponseDTO> criarManutencao(@RequestBody ManutencaoRequestDTO dto){
        return ResponseEntity.status(201).body(manutencaoService.criarManutencao(dto));
    }
    @GetMapping
    public ResponseEntity<List<ManutencaoResponseDTO>> listarTodasManutencoes(){
        return ResponseEntity.ok(manutencaoService.listarTodasManutencoes());
    }
    @GetMapping("{id}")
    public ResponseEntity<ManutencaoResponseDTO> obterManutencaoPorId(@PathVariable Integer id){
        return ResponseEntity.ok(manutencaoService.obterManutencaoPorId(id));
    }
    @PutMapping("{id}")
    public ResponseEntity<ManutencaoResponseDTO> atualizarManutencao(@RequestBody ManutencaoRequestDTO dto, @PathVariable Integer id){
        return ResponseEntity.ok(manutencaoService.atualizarManutencao(dto,id));
    }
    @DeleteMapping("{id}")
    public void deletarManutencao(@PathVariable Integer id){
        manutencaoService.deletarManutencao(id);
        ResponseEntity.noContent().build();
    }
}
