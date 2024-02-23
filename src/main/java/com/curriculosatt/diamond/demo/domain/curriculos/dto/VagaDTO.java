package com.curriculosatt.diamond.demo.domain.curriculos.dto;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO {

    @Schema(example = "1")
    private Long id;

    @NotBlank
    @Schema(example = "Desenvolvedor Java")
    private String tituloVaga;

    @Schema(example = "2024-02-14T00:00:00Z")
    private Date dataInicio;

    @NotBlank
    @Schema(example = "2024-02-28T00:00:00Z")
    private Date dataExpiracao;

    @Schema(example = "Descrição da vaga")
    private String descricaoVaga;

    @Schema(example = "5000.00")
    private Float salario;

    @Schema(example = "0")
    private Integer totalDeCandidatosAplicado;


//    public void setCandidatos(List<Candidato> candidatos) {
//    }
}
