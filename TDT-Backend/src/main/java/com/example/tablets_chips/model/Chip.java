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
    private List<TabletsChips> tabletsChips;
}