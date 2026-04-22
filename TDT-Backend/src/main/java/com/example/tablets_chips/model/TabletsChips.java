package com.example.tablets_chips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tablets_chips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TabletsChips {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tc_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tab_id")
    private Tablet tablet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chp_id")
    private Chip chip;

    @JsonIgnore
    @OneToMany(mappedBy = "tabletsChips")
    private List<Devolucao> devolucoes;

    @JsonIgnore
    @OneToMany(mappedBy = "tabletsChips")
    private List<Manutencao> manutencoes;
}