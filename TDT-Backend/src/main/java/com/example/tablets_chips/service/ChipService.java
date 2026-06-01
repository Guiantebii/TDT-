package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.ChipRequestDTO;
import com.example.tablets_chips.dto.ChipResponseDTO;
import com.example.tablets_chips.exception.BusinessException;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Chip;
import com.example.tablets_chips.model.TabletsChips;
import com.example.tablets_chips.repository.ChipRepository;
import com.example.tablets_chips.repository.DevolucaoRepository;
import com.example.tablets_chips.repository.ManutencaoRepository;
import com.example.tablets_chips.repository.TabletsChipsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChipService {

    private final ChipRepository chipRepository;

    private  final ManutencaoRepository manutencaoRepository;
    private final DevolucaoRepository devolucaoRepository;
    private final TabletsChipsRepository tabletsChipsRepository;
    public ChipService(ChipRepository chipRepository, ManutencaoRepository manutencaoRepository, DevolucaoRepository devolucaoRepository, TabletsChipsRepository tabletsChipsRepository) {
        this.chipRepository = chipRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.devolucaoRepository = devolucaoRepository;
        this.tabletsChipsRepository = tabletsChipsRepository;
    }

    public List<ChipResponseDTO> listarTodos() {
        return chipRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ChipResponseDTO buscarPorId(Integer id) {
        Chip chip = chipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chip não encontrado"));

        return toDTO(chip);
    }

    public ChipResponseDTO criar(ChipRequestDTO dto) {
        Chip chip = new Chip();

        if (chipRepository.existsByIccid(dto.iccid())) {
            throw new BusinessException("Este ICCID já está cadastrado.");
        }

        chip.setIccid(dto.iccid());
        chip.setStatus(dto.status());
        chip.setPuk(dto.puk());
        chip.setPuk2(dto.puk2());
        chip.setPin(dto.pin());
        chip.setPin2(dto.pin2());

        return toDTO(chipRepository.save(chip));
    }

    public ChipResponseDTO atualizar(Integer id, ChipRequestDTO dto) {
        Chip chip = chipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chip não encontrado"));


        chipRepository.findByIccid(dto.iccid()).ifPresent(outroChip -> {
            if (!outroChip.getId().equals(id)) {
                throw new BusinessException("Este ICCID já está cadastrado em outro chip.");
            }
        });

        chip.setIccid(dto.iccid());
        chip.setStatus(dto.status());
        chip.setPuk(dto.puk());
        chip.setPuk2(dto.puk2());
        chip.setPin(dto.pin());
        chip.setPin2(dto.pin2());

        return toDTO(chipRepository.save(chip));
    }

    @Transactional
    public void deletar(Integer id) {
        if (!chipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chip não encontrado");
        }

        // Remove os vínculos deste chip com qualquer tablet e limpa as manutenções associadas
        List<TabletsChips> vinculos = tabletsChipsRepository.findByChipId(id);
        for (TabletsChips vinculo : vinculos) {
            manutencaoRepository.deleteByTabletsChipsId(vinculo.getId());
            tabletsChipsRepository.delete(vinculo);
        }

        chipRepository.deleteById(id);
    }

    private ChipResponseDTO toDTO(Chip chip) {
        return new ChipResponseDTO(
                chip.getId(),
                chip.getIccid(),
                chip.getStatus(),
                chip.getPin(),
                chip.getPin2(),
                chip.getPuk(),
                chip.getPuk2()
        );
    }
}