package com.example.tablets_chips.dto;

public record TabletResponseDTO(

        Integer id,
        String imei,
        String ns,
        String chipIccid,
        String chipStatus

) {}