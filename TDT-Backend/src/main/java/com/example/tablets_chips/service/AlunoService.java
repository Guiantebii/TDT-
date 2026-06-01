package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.AlunoRequestDTO;
import com.example.tablets_chips.dto.AlunoResponseDTO;
import com.example.tablets_chips.exception.BusinessException;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Aluno;
import com.example.tablets_chips.model.Tablet;
import com.example.tablets_chips.repository.AlunoRepository;
import com.example.tablets_chips.repository.DevolucaoRepository;
import com.example.tablets_chips.repository.TabletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TabletRepository tabletRepository;
    private final DevolucaoRepository devolucaoRepository;

    public AlunoService(AlunoRepository alunoRepository, TabletRepository tabletRepository, DevolucaoRepository devolucaoRepository) {
        this.alunoRepository = alunoRepository;
        this.tabletRepository = tabletRepository;
        this.devolucaoRepository = devolucaoRepository;
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
        if (alunoRepository.existsByEol(dto.eol())) {
            throw new BusinessException("Já existe um aluno cadastrado com este EOL.");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEol(dto.eol());
        aluno.setTurma(dto.turma());
        aluno.setTel1(dto.tel1());
        aluno.setTel2(dto.tel2());
        aluno.setDataNasc(dto.dataNasc());

        // 2. Só valida e busca o tablet se o id não for nulo
        if (dto.tabletId() != null) {
            if (alunoRepository.existsByTabletId(dto.tabletId())) {
                throw new BusinessException("Este tablet já está vinculado a outro aluno.");
            }
            Tablet tablet = tabletRepository.findById(dto.tabletId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));
            aluno.setTablet(tablet);
        } else {
            aluno.setTablet(null);
        }

        return toDTO(alunoRepository.save(aluno));
    }

    public AlunoResponseDTO atualizar(Integer id, AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));


        alunoRepository.findByEol(dto.eol()).ifPresent(outroAluno -> {
            if (!outroAluno.getId().equals(id)) {
                throw new BusinessException("Já existe outro aluno cadastrado com este EOL.");
            }
        });

        if (dto.tabletId() != null) {
            alunoRepository.findByTabletId(dto.tabletId()).ifPresent(outroAluno -> {
                if (!outroAluno.getId().equals(id)) {
                    throw new BusinessException("Este tablet já está associado a outro aluno.");
                }
            });

            Tablet tablet = tabletRepository.findById(dto.tabletId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));
            aluno.setTablet(tablet);
        } else {

            aluno.setTablet(null);
        }

        aluno.setNome(dto.nome());
        aluno.setEol(dto.eol());
        aluno.setTurma(dto.turma());
        aluno.setTel1(dto.tel1());
        aluno.setTel2(dto.tel2());
        aluno.setDataNasc(dto.dataNasc());

        return toDTO(alunoRepository.save(aluno));
    }

    @Transactional // 🔥 Garante a segurança da operação em lote
    public void deletarAluno(Integer id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno não encontrado");
        }

        // PASSO 1: Apagar todo o histórico de devoluções associado a este aluno
        devolucaoRepository.deleteByAlunoId(id);

        // PASSO 2: Agora o aluno pode ser removido sem deixar registros órfãos
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