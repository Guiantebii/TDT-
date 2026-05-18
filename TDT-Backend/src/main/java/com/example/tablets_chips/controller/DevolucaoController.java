package com.example.tablets_chips.controller;

import com.example.tablets_chips.dto.DevolucaoRequestDTO;
import com.example.tablets_chips.dto.DevolucaoResponseDTO;
import com.example.tablets_chips.service.DevolucaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("devolucoes")
public class DevolucaoController {
    private final DevolucaoService devolucaoService;

    public DevolucaoController(DevolucaoService devolucaoService) {
        this.devolucaoService = devolucaoService;
    }

    @PostMapping
    public ResponseEntity<DevolucaoResponseDTO> criarDevolucao(@RequestBody DevolucaoRequestDTO dto){
        return ResponseEntity.status(201).body(devolucaoService.criarDevolucao(dto));
    }

    @GetMapping
    public ResponseEntity<List<DevolucaoResponseDTO>> listarTodasDevolucoes(){
        return ResponseEntity.ok(devolucaoService.listarTodasDevolucoes());
    }

    @GetMapping("{id}")
    public ResponseEntity<DevolucaoResponseDTO> listarDevolucaoPorId(@PathVariable Integer id){
        return ResponseEntity.ok(devolucaoService.listarDevolucaoPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DevolucaoResponseDTO> atualizarDevolucao(@RequestBody DevolucaoRequestDTO dto,@PathVariable Integer id) {
        return ResponseEntity.ok(devolucaoService.atualizarDevolucao(dto,id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarDevolucao(@PathVariable Integer id){
       devolucaoService.deletarDevolucao(id);
       return ResponseEntity.noContent().build();
    }
}
