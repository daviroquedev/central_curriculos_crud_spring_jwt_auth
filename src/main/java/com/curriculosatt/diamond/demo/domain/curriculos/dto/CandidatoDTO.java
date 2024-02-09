package com.curriculosatt.diamond.demo.domain.curriculos.dto;

import com.curriculosatt.diamond.demo.domain.curriculos.Candidatos.CandidatoRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CandidatoDTO(Long id,
                               @NotBlank
                               String nome,
                               @NotBlank
                               @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 999.999.999-99")
                               String cpf,
                               String dataNascimento,
                               @NotBlank
                               @Email
                               String email,
                               String telefone,
                               String escolaridade,
                               String funcao,
                               List<String> listaCompetencias,
                               String statusSolicitacao,
                               CandidatoRole role

) {

}
