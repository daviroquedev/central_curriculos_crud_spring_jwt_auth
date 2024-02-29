package com.curriculosatt.diamond.demo.domain.curriculos.dto;

import java.util.Map;

public class CandidatoResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Map<String, Integer> competencias;
    private String statusSolicitacao;

    public CandidatoResponseDTO(Long id, String nome, String cpf, String email, String telefone, Map<String, Integer> competencias, String statusSolicitacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.competencias = competencias;
        this.statusSolicitacao = statusSolicitacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Map<String, Integer> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Map<String, Integer> competencias) {
        this.competencias = competencias;
    }

    public String getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public void setStatusSolicitacao(String statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }
}
