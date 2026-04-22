package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Chip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChipRepository extends JpaRepository<Chip, Integer> {
}
