package com.curriculosatt.diamond.demo.domain.curriculos.controllers;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Vaga;
import com.curriculosatt.diamond.demo.domain.curriculos.VagaNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @PostMapping
    public ResponseEntity<Vaga> criarVaga(@RequestBody Vaga vaga) {
        Vaga novaVaga = vagaRepository.save(vaga);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVaga);
    }

    @GetMapping
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaRepository.findAll();
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarVagaPorId(@PathVariable Long id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + id));
        return ResponseEntity.ok(vaga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizarVaga(@PathVariable Long id, @RequestBody Vaga vagaAtualizada) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + id));

        // Atualizar os campos da vaga com os novos valores
        vaga.setTituloVaga(vagaAtualizada.getTituloVaga());
        vaga.setDataInicio(vagaAtualizada.getDataInicio());
        vaga.setDataExpiracao(vagaAtualizada.getDataExpiracao());
        vaga.setDescricaoVaga(vagaAtualizada.getDescricaoVaga());
        vaga.setSalario(vagaAtualizada.getSalario());
        vaga.setTotalDeCandidatosAplicado(vagaAtualizada.getTotalDeCandidatosAplicado());
        // Atualize outros campos conforme necessário

        vagaAtualizada = vagaRepository.save(vaga);
        return ResponseEntity.ok(vagaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVaga(@PathVariable Long id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + id));

        vagaRepository.delete(vaga);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{vagaId}/candidatar")
    public ResponseEntity<String> candidatarAVaga(@PathVariable Long vagaId, @RequestBody Candidato candidato) {
        Vaga vaga = vagaRepository.findById(vagaId)
                .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + vagaId));

        // Adicionando o candidato à lista de candidatos da vaga
        vaga.getCandidatos().add(candidato);
        vagaRepository.save(vaga);

        return ResponseEntity.ok("Candidato cadastrado na vaga com sucesso!");
    }
}