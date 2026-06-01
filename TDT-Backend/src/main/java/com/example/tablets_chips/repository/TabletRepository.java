package com.example.tablets_chips.repository;

import com.example.tablets_chips.model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TabletRepository extends JpaRepository<Tablet, Integer> {

    boolean existsByImei(String imei);
    boolean existsByNs(String ns);
    Optional<Tablet> findByImei(String imei);
    Optional<Tablet> findByNs(String ns);
}