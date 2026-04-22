package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
}
