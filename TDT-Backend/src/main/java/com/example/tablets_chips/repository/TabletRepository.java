package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TabletRepository extends JpaRepository<Tablet, Integer> {

    Optional<Tablet> findByImei(String imei);

    boolean existsByImei(String imei);
}