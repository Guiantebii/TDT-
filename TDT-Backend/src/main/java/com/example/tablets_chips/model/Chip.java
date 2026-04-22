package com.example.tablets_chips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chip {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public String getPuk2() {
        return puk2;
    }

    public void setPuk2(String puk2) {
        this.puk2 = puk2;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<TabletsChips> getTabletsChips() {
        return tabletsChips;
    }

    public void setTabletsChips(List<TabletsChips> tabletsChips) {
        this.tabletsChips = tabletsChips;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chp_id")
    private Integer id;

    @Column(name = "chp_iccid")
    private String iccid;

    @Column(name = "chp_status")
    private String status;

    @Column(name = "chp_puk")
    private String puk;

    @Column(name = "chp_puk2")
    private String puk2;

    @Column(name = "chp_pin")
    private String pin;

    @Column(name = "chp_pin2")
    private String pin2;

    @JsonIgnore
    @OneToMany(mappedBy = "chip")
    private List<Aluno> alunos;

    @JsonIgnore
    @OneToMany(mappedBy = "chip")
    private List<TabletsChips> tabletsChips;
}