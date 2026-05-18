package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.DevolucaoRequestDTO;
import com.example.tablets_chips.dto.DevolucaoResponseDTO;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.*;
import com.example.tablets_chips.repository.AlunoRepository;
import com.example.tablets_chips.repository.DevolucaoRepository;
import com.example.tablets_chips.repository.TabletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevolucaoService {

    private final DevolucaoRepository devolucaoRepository;
    private final TabletRepository tabletRepository;
    private final AlunoRepository alunoRepository;

    public DevolucaoService(DevolucaoRepository devolucaoRepository, TabletRepository tabletRepository,AlunoRepository alunoRepository) {
        this.devolucaoRepository = devolucaoRepository;
        this.tabletRepository = tabletRepository;
        this.alunoRepository = alunoRepository;
    }



    public DevolucaoResponseDTO criarDevolucao(DevolucaoRequestDTO dto){
        Devolucao devolucao = new Devolucao();

        Tablet tablet = tabletRepository.findById(dto.tab_id()).orElseThrow(()-> new ResourceNotFoundException("Tablet não encontrado"));

        devolucao.setTablet(tablet);

        Aluno aluno = alunoRepository.findById(dto.alu_id()).orElseThrow(()-> new ResourceNotFoundException("Aluno não encontrado"));

        devolucao.setAluno(aluno);

        devolucao.setDataEntrega(dto.dataEntrega());
        devolucao.setDataDevolucao(dto.dataDevolucao());

        return toDTO(devolucaoRepository.save(devolucao));
    }

    public List<DevolucaoResponseDTO> listarTodasDevolucoes() {
        return devolucaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public DevolucaoResponseDTO listarDevolucaoPorId(Integer id){
        Devolucao devolucao = devolucaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Devolução não encontrada"));
        return toDTO(devolucao);
    }

    public DevolucaoResponseDTO atualizarDevolucao(DevolucaoRequestDTO dto,Integer id){
        Devolucao devolucao = devolucaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Devolução não encontrada"));

        Tablet tablet = tabletRepository.findById(dto.tab_id()).orElseThrow(()-> new ResourceNotFoundException("Tablet não encontrado"));

        Aluno aluno = alunoRepository.findById(dto.alu_id()).orElseThrow(()-> new ResourceNotFoundException("Aluno não encontrado"));

        devolucao.setTablet(tablet);
        devolucao.setAluno(aluno);
        devolucao.setDataDevolucao(dto.dataDevolucao());
        devolucao.setDataEntrega(dto.dataEntrega());

        return toDTO(devolucaoRepository.save(devolucao));

    }
    public void deletarDevolucao(Integer id){
        if(!devolucaoRepository.existsById(id)){
            throw new ResourceNotFoundException("Aluno não encontrado");
        }
        devolucaoRepository.deleteById(id);
    }

    private DevolucaoResponseDTO toDTO(Devolucao devolucao) {
        return new DevolucaoResponseDTO(
                devolucao.getId(),
                devolucao.getTablet().getId(),
                devolucao.getAluno().getId(),
                devolucao.getDataEntrega(),
                devolucao.getDataDevolucao()
        );
    }
}
