package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.ChipRequestDTO;
import com.example.tablets_chips.dto.ChipResponseDTO;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Chip;
import com.example.tablets_chips.repository.ChipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChipService {

    private final ChipRepository chipRepository;

    public ChipService(ChipRepository chipRepository) {
        this.chipRepository = chipRepository;
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

        chip.setIccid(dto.iccid());
        chip.setStatus(dto.status());
        chip.setPuk(dto.puk());
        chip.setPuk2(dto.puk2());
        chip.setPin(dto.pin());
        chip.setPin2(dto.pin2());

        return toDTO(chipRepository.save(chip));
    }

    public void deletar(Integer id) {
        if (!chipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Chip não encontrado");
        }
        chipRepository.deleteById(id);
    }

    private ChipResponseDTO toDTO(Chip chip) {
        return new ChipResponseDTO(
                chip.getId(),
                chip.getIccid(),
                chip.getStatus()
        );
    }
}