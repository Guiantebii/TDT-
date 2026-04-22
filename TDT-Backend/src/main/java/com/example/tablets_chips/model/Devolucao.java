package com.example.tablets_chips.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "devolucao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Devolucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dvl_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tc_id")
    private TabletsChips tabletsChips;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alu_id")
    private Aluno aluno;

    @Column(name = "dvl_data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "dvl_data_devolucao")
    private LocalDate dataDevolucao;
}