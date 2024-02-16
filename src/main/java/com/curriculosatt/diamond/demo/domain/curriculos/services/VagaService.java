package com.curriculosatt.diamond.demo.domain.curriculos.services;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Vaga;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.VagaNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.CandidatoRepository;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;

    public String candidatarAVaga(Long vagaId, String cpf) {
        // Buscar candidato pelo CPF no banco de dados
        Candidato candidato = (Candidato) candidatoRepository.findByCpf(cpf);
        if (candidato == null) {
            return "Candidato não encontrado";
        }

        Vaga vaga = vagaRepository.findById(vagaId)
                .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + vagaId));

        boolean candidatoJaAlistado = vaga.getCandidatos().stream()
                .anyMatch(c -> c.getId().equals(candidato.getId()));

        if (candidatoJaAlistado) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Candidato já está alistado nesta vaga!");
        }

        int totalDeCandidatosAplicado = vaga.getTotalDeCandidatosAplicado() + 1;
        vaga.setTotalDeCandidatosAplicado(totalDeCandidatosAplicado);

        vaga.getCandidatos().add(candidato);
        vagaRepository.save(vaga);

        return "Candidato cadastrado na vaga com sucesso!";
    }
}