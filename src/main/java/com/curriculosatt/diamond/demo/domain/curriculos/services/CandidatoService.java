package com.curriculosatt.diamond.demo.domain.curriculos.services;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.enums.CandidatoRole;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CandidatoService {

    public Candidato convertToEntity(CandidatoDTO candidatoDTO) {
        Candidato candidato = new Candidato();
        candidato.setNome(candidatoDTO.nome());
        candidato.setCpf(candidatoDTO.cpf());
        candidato.setEmail(candidatoDTO.email());
        candidato.setDataNascimento(Date.valueOf(candidatoDTO.dataNascimento()));
        candidato.setTelefone(candidatoDTO.telefone());
        candidato.setEscolaridade(candidatoDTO.escolaridade() != null ? candidatoDTO.escolaridade().name() : null);
        candidato.setFuncao(candidatoDTO.funcao());
        candidato.setListaCompetencias(candidatoDTO.listaCompetencias());
        candidato.setStatusSolicitacao(candidatoDTO.statusSolicitacao());
        candidato.setRole(candidatoDTO.role() != null ? candidatoDTO.role() : CandidatoRole.USER);

        return candidato;
    }

}