package com.example.tablets_chips.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manutencao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mnu_id")
    private Integer id;

    @Column(name = "mnu_descricao")
    private String descricao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tc_id")
    private TabletsChips tabletsChips;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tab_id")
    private Tablet tablet;
}