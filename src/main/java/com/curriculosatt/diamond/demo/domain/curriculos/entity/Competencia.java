package com.curriculosatt.diamond.demo.domain.curriculos.entity;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="competencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "competencia")
    private String competencia;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;
}