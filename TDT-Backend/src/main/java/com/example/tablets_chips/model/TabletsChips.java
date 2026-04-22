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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public List<Devolucao> getDevolucoes() {
        return devolucoes;
    }

    public void setDevolucoes(List<Devolucao> devolucoes) {
        this.devolucoes = devolucoes;
    }

    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }

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