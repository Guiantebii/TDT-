package com.example.tablets_chips.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TabletRequestDTO(

        @NotBlank(message = "IMEI é obrigatório")
        @Pattern(regexp = "\\d{15}", message = "IMEI deve conter 15 dígitos numéricos")
        String imei,

        @NotBlank(message = "Número de série é obrigatório")
        String ns

) {}