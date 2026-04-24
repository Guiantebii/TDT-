package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.AlunoRequestDTO;
import com.example.tablets_chips.dto.AlunoResponseDTO;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Aluno;
import com.example.tablets_chips.model.Tablet;
import com.example.tablets_chips.repository.AlunoRepository;
import com.example.tablets_chips.repository.TabletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TabletRepository tabletRepository;

    public AlunoService(AlunoRepository alunoRepository, TabletRepository tabletRepository) {
        this.alunoRepository = alunoRepository;
        this.tabletRepository = tabletRepository;
    }

    public List<AlunoResponseDTO> listarTodosAlunos() {
        return alunoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public AlunoResponseDTO buscarPorId(Integer id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        return toDTO(aluno);
    }

    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();

        aluno.setNome(dto.nome());
        aluno.setEol(dto.eol());
        aluno.setTurma(dto.turma());
        aluno.setTel1(dto.tel1());
        aluno.setTel2(dto.tel2());
        aluno.setDataNasc(dto.dataNasc());

        Tablet tablet = tabletRepository.findById(dto.tabletId())
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        aluno.setTablet(tablet);

        return toDTO(alunoRepository.save(aluno));
    }

    public AlunoResponseDTO atualizar(Integer id, AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        aluno.setNome(dto.nome());
        aluno.setEol(dto.eol());
        aluno.setTurma(dto.turma());
        aluno.setTel1(dto.tel1());
        aluno.setTel2(dto.tel2());
        aluno.setDataNasc(dto.dataNasc());

        Tablet tablet = tabletRepository.findById(dto.tabletId())
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        aluno.setTablet(tablet);

        return toDTO(alunoRepository.save(aluno));
    }

    public void deletar(Integer id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

    private AlunoResponseDTO toDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEol(),
                aluno.getDataNasc(),
                aluno.getTurma(),
                aluno.getTel1(),
                aluno.getTel2(),
                aluno.getTablet() != null ? aluno.getTablet().getId() : null,
                aluno.getTablet() != null ? aluno.getTablet().getNs() : null
        );
    }
}