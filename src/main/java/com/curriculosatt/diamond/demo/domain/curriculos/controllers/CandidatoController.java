package com.curriculosatt.diamond.demo.domain.curriculos.controllers;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.CandidatoNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.CandidatoRepository;
import com.curriculosatt.diamond.demo.domain.curriculos.services.CandidatoService;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/candidatos")
@RequiredArgsConstructor
public class CandidatoController {


    private final CandidatoRepository candidatoRepository;
    private final CandidatoService candidatoService;

    @PostMapping
    public ResponseEntity<Candidato> criarCandidato(@Valid @RequestBody CandidatoDTO candidatoDTO) {
        Candidato candidato = candidatoService.convertToEntity(candidatoDTO);
        candidato = candidatoRepository.save(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidato);
    }

    @GetMapping
    public ResponseEntity<List<Candidato>> listarCandidatos() {
        List<Candidato> candidatos = candidatoRepository.findAllCandidatos();
        return ResponseEntity.status(HttpStatus.OK).body(candidatos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Candidato> buscarCandidatoPorEmail(@RequestParam String email) {
        Candidato candidato = candidatoRepository.findByEmail(email)
                .orElseThrow(() -> new CandidatoNotFoundException("Candidato não encontrado com o e-mail: " + email));
        return ResponseEntity.ok(candidato);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> buscarCandidatoPorId(@PathVariable Long id) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new CandidatoNotFoundException("Candidato não encontrado com o ID: " + id));
        return ResponseEntity.ok(candidato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> atualizarCandidato(@PathVariable Long id, @Valid @RequestBody CandidatoDTO candidatoDTO) {
        Candidato candidato = candidatoService.convertToEntity(candidatoDTO);
        candidato.setId(id);
        candidato = candidatoRepository.save(candidato);
        return ResponseEntity.ok(candidato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCandidato(@PathVariable Long id) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new CandidatoNotFoundException("Candidato não encontrado com o ID: " + id));

        candidatoRepository.delete(candidato);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/gerenciarSolicitacao")
    public ResponseEntity<Candidato> gerenciarSolicitacao(
            @PathVariable Long id,
            @RequestParam String status) {
        Candidato candidato = candidatoService.atualizarStatusSolicitacao(id, status);
        return ResponseEntity.ok(candidato);
    }
}