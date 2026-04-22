package com.example.tablets_chips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tablet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tablet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tab_id")
    private Integer id;

    @Column(name = "tab_imei")
    private String imei;

    @Column(name = "tab_ns")
    private String ns;

    @JsonIgnore
    @OneToMany(mappedBy = "tablet")
    private List<TabletsChips> tabletsChips;

    @JsonIgnore
    @OneToMany(mappedBy = "tablet")
    private List<Manutencao> manutencoes;
}