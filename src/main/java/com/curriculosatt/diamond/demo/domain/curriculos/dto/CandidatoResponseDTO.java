package com.curriculosatt.diamond.demo.domain.curriculos.dto;

import com.curriculosatt.diamond.demo.domain.curriculos.enums.Escolaridade;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CandidatoResponseDTO {


    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String escolaridade;
    private String funcao;
    private Map<String, Integer> competencias;
    private String statusSolicitacao;

    public CandidatoResponseDTO(Long id, String nome, String cpf, String email, String telefone, Map<String, Integer> competencias, String statusSolicitacao, String funcao, String escolaridade) {
        this.id = id;
        this.nome = nome;
        this.escolaridade = escolaridade;
        this.funcao = funcao;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.competencias = competencias;
        this.statusSolicitacao = statusSolicitacao;
    }


}
