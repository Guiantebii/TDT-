package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsByTabletId(Integer tabletId);
    boolean existsByEol(String eol);
    Optional<Aluno> findByEol(String eol);
    Optional<Aluno> findByTabletId(Integer tabletId);
}
