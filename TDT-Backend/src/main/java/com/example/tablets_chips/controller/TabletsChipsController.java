package com.example.tablets_chips.controller;

import com.example.tablets_chips.dto.TabletsChipsResponseDto;
import com.example.tablets_chips.service.TabletsChipsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tablets-chips")
@RequiredArgsConstructor
public class TabletsChipsController {

    private final TabletsChipsService tabletsChipsService;

    @GetMapping
    public ResponseEntity<List<TabletsChipsResponseDto>> listarTodosVinculados() {
        return ResponseEntity.ok(tabletsChipsService.listarTodosVinculados());
    }
}