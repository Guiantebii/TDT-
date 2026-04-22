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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public List<TabletsChips> getTabletsChips() {
        return tabletsChips;
    }

    public void setTabletsChips(List<TabletsChips> tabletsChips) {
        this.tabletsChips = tabletsChips;
    }

    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }

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