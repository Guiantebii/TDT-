package com.example.tablets_chips.dto;

public record TabletsChipsResponseDto(
        Integer id,
        String tabletImei,
        String tabletNs,
        String chipIccid
) {
}
