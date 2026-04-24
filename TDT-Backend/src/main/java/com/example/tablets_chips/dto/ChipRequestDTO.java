package com.example.tablets_chips.dto;

import jakarta.validation.constraints.NotBlank;

public record ChipRequestDTO (
        @NotBlank(message = "ICCID é obrigatório")
        String iccid,

        @NotBlank(message = "Status é obrigatório")
        String status,

        String puk,
        String puk2,
        String pin,
        String pin2
){
}
