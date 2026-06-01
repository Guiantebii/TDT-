package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Chip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChipRepository extends JpaRepository<Chip, Integer> {
    boolean existsByIccid(String iccid);
    Optional<Chip> findByIccid(String iccid);
}
