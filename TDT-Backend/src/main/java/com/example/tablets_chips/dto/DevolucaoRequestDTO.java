package com.example.tablets_chips.dto;

import java.time.LocalDate;

public record DevolucaoRequestDTO(
        Integer tab_id,
        Integer alu_id,
        LocalDate dataEntrega,
        LocalDate dataDevolucao
) {
}
