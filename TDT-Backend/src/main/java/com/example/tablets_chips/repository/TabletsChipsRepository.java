package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.TabletsChips;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabletsChipsRepository extends JpaRepository<TabletsChips, Integer> {
    boolean existsByTabletId(Integer tabletId);
    boolean existsByChipId(Integer chipId);
    List<TabletsChips> findByTabletId(Integer tabletId);
    List<TabletsChips> findByChipId(Integer chipId);
}
