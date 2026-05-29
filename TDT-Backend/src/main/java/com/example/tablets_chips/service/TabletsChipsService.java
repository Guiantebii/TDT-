package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.TabletsChipsResponseDto;
import com.example.tablets_chips.repository.TabletsChipsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TabletsChipsService {

    private final TabletsChipsRepository repository;


    public List<TabletsChipsResponseDto> listarTodosVinculados() {
        return repository.findAll().stream()
                .map(tc -> new TabletsChipsResponseDto(
                        tc.getId(),
                        tc.getTablet().getImei(),
                        tc.getTablet().getNs(),
                        tc.getChip().getIccid()
                ))
                .toList();
    }
}