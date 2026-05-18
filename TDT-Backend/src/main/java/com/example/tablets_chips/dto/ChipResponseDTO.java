package com.example.tablets_chips.dto;

public record ChipResponseDTO (
        Integer id,
        String iccid,
        String status,
        String pin,
        String pin2,
        String puk,
        String puk2
){
}
