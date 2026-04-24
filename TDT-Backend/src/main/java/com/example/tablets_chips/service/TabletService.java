package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.TabletRequestDTO;
import com.example.tablets_chips.dto.TabletResponseDTO;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Chip;
import com.example.tablets_chips.model.Tablet;
import com.example.tablets_chips.model.TabletsChips;
import com.example.tablets_chips.repository.ChipRepository;
import com.example.tablets_chips.repository.TabletRepository;
import com.example.tablets_chips.repository.TabletsChipsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabletService {

    private final TabletRepository tabletRepository;
    private final TabletsChipsRepository tabletsChipsRepository;
    private final ChipRepository chipRepository;

    public TabletService(TabletRepository tabletRepository, TabletsChipsRepository tabletsChipsRepository, ChipRepository chipRepository) {
        this.tabletRepository = tabletRepository;
        this.tabletsChipsRepository = tabletsChipsRepository;
        this.chipRepository = chipRepository;
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
    public void vincularChip(Integer tabletId, Integer chipId) {

        Tablet tablet = tabletRepository.findById(tabletId)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        Chip chip = chipRepository.findById(chipId)
                .orElseThrow(() -> new ResourceNotFoundException("Chip não encontrado"));

        TabletsChips tc = new TabletsChips();
        tc.setTablet(tablet);
        tc.setChip(chip);

        tabletsChipsRepository.save(tc);
    }

    private TabletResponseDTO toDTO(Tablet tablet) {

        String iccid = null;
        String status = null;

        if (tablet.getTabletsChips() != null && !tablet.getTabletsChips().isEmpty()) {

            var ultimo = tablet.getTabletsChips()
                    .get(tablet.getTabletsChips().size() - 1);

            if (ultimo.getChip() != null) {
                iccid = ultimo.getChip().getIccid();
                status = ultimo.getChip().getStatus();
            }
        }

        return new TabletResponseDTO(
                tablet.getId(),
                tablet.getImei(),
                tablet.getNs(),
                iccid,
                status
        );
    }
}