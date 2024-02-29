package com.curriculosatt.diamond.demo.domain.curriculos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetenciaDTO {
    private String competencia;
    private int nivel_proficiencia;
}