package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Integer> {
    void deleteByTabletsChipsId(Integer tabletsChipsId);
}
