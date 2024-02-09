package com.curriculosatt.diamond.demo.domain.curriculos.controllers;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.Candidatos.CandidatoNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.Candidatos.CandidatoRepository;
import com.curriculosatt.diamond.demo.domain.curriculos.services.CandidatoService;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private CandidatoService candidatoService;

    @PostMapping
    public ResponseEntity<Candidato> criarCandidato(@Valid @RequestBody CandidatoDTO candidatoDTO) {
        Candidato candidato = candidatoService.convertToEntity(candidatoDTO);
        candidato = candidatoRepository.save(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidato);
    }

    @GetMapping
    public ResponseEntity<List<Candidato>> listarCandidatos() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        return ResponseEntity.ok(candidatos);
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
}