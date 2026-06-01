package com.example.tablets_chips.service;

import com.example.tablets_chips.dto.TabletRequestDTO;
import com.example.tablets_chips.dto.TabletResponseDTO;
import com.example.tablets_chips.exception.BusinessException;
import com.example.tablets_chips.exception.ResourceNotFoundException;
import com.example.tablets_chips.model.Chip;
import com.example.tablets_chips.model.Tablet;
import com.example.tablets_chips.model.TabletsChips;
import com.example.tablets_chips.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabletService {

    private final TabletRepository tabletRepository;
    private final TabletsChipsRepository tabletsChipsRepository;
    private final ChipRepository chipRepository;
    private final AlunoRepository alunoRepository;
    private final ManutencaoRepository manutencaoRepository;
    private final DevolucaoRepository devolucaoRepository;

    public TabletService(TabletRepository tabletRepository, TabletsChipsRepository tabletsChipsRepository, ChipRepository chipRepository, AlunoRepository alunoRepository, ManutencaoRepository manutencaoRepository, DevolucaoRepository devolucaoRepository) {
        this.tabletRepository = tabletRepository;
        this.tabletsChipsRepository = tabletsChipsRepository;
        this.chipRepository = chipRepository;
        this.alunoRepository = alunoRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.devolucaoRepository = devolucaoRepository;
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
        if (tabletRepository.existsByImei(dto.imei())) {
            throw new BusinessException("Este IMEI já está cadastrado.");
        }
        if (tabletRepository.existsByNs(dto.ns())) {
            throw new BusinessException("Este Número de Série já está cadastrado.");
        }

        Tablet tablet = new Tablet();
        tablet.setImei(dto.imei());
        tablet.setNs(dto.ns());

        return toDTO(tabletRepository.save(tablet));
    }

    public TabletResponseDTO atualizar(Integer id, TabletRequestDTO dto) {
        Tablet tablet = tabletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        tabletRepository.findByImei(dto.imei()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new BusinessException("Este IMEI já está cadastrado em outro tablet.");
            }
        });

        tabletRepository.findByNs(dto.ns()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new BusinessException("Este Número de Série já está cadastrado em outro tablet.");
            }
        });

        tablet.setImei(dto.imei());
        tablet.setNs(dto.ns());

        return toDTO(tabletRepository.save(tablet));
    }

    @Transactional // 🔥 Garante que toda a operação aconteça como uma única transação protegida
    public void deletar(Integer id) {
        Tablet tablet = tabletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablet não encontrado"));

        // PASSO 1: Desvincular do Aluno (Se algum aluno estiver com esse tablet, removemos o vínculo)
        alunoRepository.findByTabletId(id).ifPresent(aluno -> {
            aluno.setTablet(null); // Remove o tablet do aluno (o aluno continua existindo!)
            alunoRepository.save(aluno);
        });

        // PASSO 2: Apagar o histórico de devoluções desse tablet
        devolucaoRepository.deleteByTabletId(id);

        // PASSO 3: Limpar o histórico de Chips vinculados e suas Manutenções
        List<TabletsChips> vinculos = tabletsChipsRepository.findByTabletId(id);
        for (TabletsChips vinculo : vinculos) {
            // Como a Manutenção depende da tabela intermediária (TabletsChips), apaga a manutenção primeiro!
            manutencaoRepository.deleteByTabletsChipsId(vinculo.getId());
            // Depois apaga o vínculo (O chip físico continua intacto na tabela de Chips!)
            tabletsChipsRepository.delete(vinculo);
        }

        // PASSO 4: Agora que o tablet está 100% isolado no banco... deletamos ele!
        tabletRepository.delete(tablet);
    }
    public void vincularChip(Integer tabletId, Integer chipId) {
        if (tabletsChipsRepository.existsByTabletId(tabletId)) {
            throw new BusinessException("Este tablet já possui um chip vinculado.");
        }

        if (tabletsChipsRepository.existsByChipId(chipId)) {
            throw new BusinessException("Este chip já está vinculado a outro tablet.");
        }

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