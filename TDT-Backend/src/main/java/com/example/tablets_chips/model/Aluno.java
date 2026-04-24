package com.example.tablets_chips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alu_id")
    private Integer id;

    @Column(name = "alu_eol")
    private String eol;

    @Column(name = "alu_nome")
    private String nome;

    @Column(name = "alu_data_nasc")
    private LocalDate dataNasc;

    @Column(name = "alu_turma")
    private String turma;

    @Column(name = "alu_tel_1")
    private String tel1;

    @Column(name = "alu_tel_2")
    private String tel2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tab_id")
    private Tablet tablet;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<Devolucao> devolucoes;
}