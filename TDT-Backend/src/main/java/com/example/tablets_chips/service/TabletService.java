package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.TabletRequestDTO;
import com.example.tablets_chips.dto.TabletResponseDTO;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Tablet;
import com.example.tablets_chips.repository.TabletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabletService {

    private final TabletRepository tabletRepository;

    public TabletService(TabletRepository tabletRepository) {
        this.tabletRepository = tabletRepository;
    }

    public List<TabletResponseDTO> listarTodos() {
        return tabletRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TabletResponseDTO buscarPorId(Integer id) {
        Tablet tablet = tabletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        return toDTO(tablet);
    }

    public TabletResponseDTO criar(TabletRequestDTO dto) {

        Tablet tablet = new Tablet();
        tablet.setImei(dto.imei());
        tablet.setNs(dto.ns());

        return toDTO(tabletRepository.save(tablet));
    }

    public TabletResponseDTO atualizar(Integer id, TabletRequestDTO dto) {

        Tablet tablet = tabletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        tablet.setImei(dto.imei());
        tablet.setNs(dto.ns());

        return toDTO(tabletRepository.save(tablet));
    }

    public void deletar(Integer id) {

        if (!tabletRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tablet não encontrado");
        }

        tabletRepository.deleteById(id);
    }

    private TabletResponseDTO toDTO(Tablet tablet) {
        return new TabletResponseDTO(
                tablet.getId(),
                tablet.getImei(),
                tablet.getNs()
        );
    }
}