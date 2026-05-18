package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Devolucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevolucaoRepository extends JpaRepository<Devolucao,Integer>{
}
