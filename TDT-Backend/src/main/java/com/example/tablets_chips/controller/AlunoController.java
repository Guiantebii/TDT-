package com.example.tablets_chips.controller;

import com.example.tablets_chips.dto.AlunoRequestDTO;
import com.example.tablets_chips.dto.AlunoResponseDTO;
import com.example.tablets_chips.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }
    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> listarTodosAlunos(){
        return ResponseEntity.ok(alunoService.listarTodosAlunos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(alunoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<AlunoResponseDTO> criar(@RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(alunoService.criarAluno(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(@PathVariable Integer id, @RequestBody AlunoRequestDTO dto){
        try {
            return ResponseEntity.ok(alunoService.atualizar(id,dto));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Integer id){
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
