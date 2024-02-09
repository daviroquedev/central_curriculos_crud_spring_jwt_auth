package com.curriculosatt.diamond.demo.domain.curriculos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name="vaga")
@Table(name = "vaga")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo_vaga", nullable = false)
    private String tituloVaga;

    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Column(name = "data_expiracao", nullable = false)
    private Date dataExpiracao;

    @Column(name = "descricao_vaga", nullable = false)
    private String descricaoVaga;

    private Float salario;

    @Column(name = "total_de_candidatos_aplicado")
    private Integer totalDeCandidatosAplicado;

    @ManyToMany
    @JoinTable(
            name = "candidato_vaga",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "candidato_id")
    )
    private List<Candidato> candidatos;

}
