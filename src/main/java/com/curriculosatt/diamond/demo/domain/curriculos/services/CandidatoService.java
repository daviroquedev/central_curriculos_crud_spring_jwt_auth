package com.curriculosatt.diamond.demo.domain.curriculos.services;

import com.curriculosatt.diamond.demo.domain.curriculos.dto.CompetenciaDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.enums.CandidatoRole;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.CandidatoNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.CandidatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoService {
    private final CandidatoRepository candidatoRepository;

    public Candidato convertToEntity(CandidatoDTO candidatoDTO) {
        Candidato candidato = new Candidato();
        candidato.setNome(candidatoDTO.nome());
        candidato.setCpf(candidatoDTO.cpf());
        candidato.setEmail(candidatoDTO.email());
        candidato.setDataNascimento(candidatoDTO.dataNascimento());
        candidato.setTelefone(candidatoDTO.telefone());
        candidato.setEscolaridade(candidatoDTO.escolaridade() != null ? candidatoDTO.escolaridade().name() : null);
        candidato.setFuncao(candidatoDTO.funcao());
        candidato.setStatusSolicitacao(candidatoDTO.statusSolicitacao());
        candidato.setRole(candidatoDTO.role() != null ? candidatoDTO.role() : CandidatoRole.USER);

        // Mapeando as competências corretamente
        List<CompetenciaDTO> competenciasDTO = candidatoDTO.competencias();
        Map<String, Integer> competencias = new HashMap<>();
        for (CompetenciaDTO competenciaDTO : competenciasDTO) {
            competencias.put(competenciaDTO.getCompetencia(), competenciaDTO.getNivel_proficiencia());
        }
        candidato.setCompetencias(competencias);

        return candidato;
    }

    public Candidato findById(Long id) {
        return candidatoRepository.findById(id)
                .orElseThrow(() -> new CandidatoNotFoundException("Candidato não encontrado com o ID: " + id));
    }

    public Candidato atualizarStatusSolicitacao(Long id, String status) {
        Candidato candidato = findById(id);

        // Verificar se o status fornecido é válido (aprovado ou reprovado)
        if (!status.equalsIgnoreCase("aprovado") && !status.equalsIgnoreCase("reprovado")) {
            throw new IllegalArgumentException("Status inválido. Use 'aprovado' ou 'reprovado'.");
        }

        // Atualizar o status do candidato
        candidato.setStatusSolicitacao(status);

        // Salvar e retornar o candidato atualizado
        return candidatoRepository.save(candidato);
    }

}