package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.ManutencaoRequestDTO;
import com.example.tablets_chips.dto.ManutencaoResponseDTO;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Manutencao;
import com.example.tablets_chips.model.TabletsChips;
import com.example.tablets_chips.repository.ManutencaoRepository;
import com.example.tablets_chips.repository.TabletsChipsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManutencaoService {
    private final ManutencaoRepository manutencaoRepository;
    private final TabletsChipsRepository tabletsChipsRepository;

    public ManutencaoService(ManutencaoRepository manutencaoRepository, TabletsChipsRepository tabletsChipsRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.tabletsChipsRepository = tabletsChipsRepository;
    }

    public ManutencaoResponseDTO criarManutencao(ManutencaoRequestDTO dto){
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao(dto.descricao());
        TabletsChips tabletsChips = tabletsChipsRepository.findById(dto.tc_id()).orElseThrow(()-> new ResourceNotFoundException("Tablets e chips não encontrados"));
        manutencao.setTabletsChips(tabletsChips);
        return toDTO(manutencaoRepository.save(manutencao));
    }

    public List<ManutencaoResponseDTO> listarTodasManutencoes(){
        return manutencaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ManutencaoResponseDTO obterManutencaoPorId(Integer id){
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manutencao Nao Encontrada"));

        return toDTO(manutencao);
    }

    public ManutencaoResponseDTO atualizarManutencao(ManutencaoRequestDTO dto, Integer id){
        Manutencao manutencao = manutencaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manutencao nao encontrada"));

        TabletsChips tabletsChips = tabletsChipsRepository.findById(dto.tc_id()).orElseThrow(() -> new  ResourceNotFoundException("Tablets e chips nao encontrados"));
        manutencao.setTabletsChips(tabletsChips);
        manutencao.setDescricao(dto.descricao());

        return toDTO(manutencaoRepository.save(manutencao));
    }

    private ManutencaoResponseDTO toDTO(Manutencao manutencao) {
        return new ManutencaoResponseDTO(
                manutencao.getId(),
                manutencao.getDescricao(),
                manutencao.getTabletsChips().getId()
        );
    }


    public void deletarManutencao(Integer id) {
        if(!manutencaoRepository.existsById(id)){
            throw new EntityNotFoundException("Manutencao nao encontrada");
        }
        manutencaoRepository.deleteById(id);
    }
}
