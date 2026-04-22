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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public TabletsChips getTabletsChips() {
        return tabletsChips;
    }

    public void setTabletsChips(TabletsChips tabletsChips) {
        this.tabletsChips = tabletsChips;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alu_id")
    private Aluno aluno;

    @Column(name = "dvl_data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "dvl_data_devolucao")
    private LocalDate dataDevolucao;
}