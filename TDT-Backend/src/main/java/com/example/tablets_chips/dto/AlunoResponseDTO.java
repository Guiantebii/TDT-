package com.example.tablets_chips.dto;

import java.time.LocalDate;

public record AlunoResponseDTO(
        Integer id,
        String nome,
        String eol,
        LocalDate dataNasc,
        String turma,
        String tel1,
        String tel2,
        Integer tabletId,
        String tabletNs
) {}