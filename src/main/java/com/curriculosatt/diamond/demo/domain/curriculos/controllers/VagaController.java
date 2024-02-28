package com.curriculosatt.diamond.demo.domain.curriculos.controllers;

import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.VagaDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Vaga;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.CandidatoNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.ErrorResponse;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.VagaNotFoundException;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.CandidatoRepository;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.VagaRepository;
import com.curriculosatt.diamond.demo.domain.curriculos.services.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaController {


    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VagaService vagaService;

    @PostMapping
    public ResponseEntity<VagaDTO> criarVaga(@RequestBody VagaDTO vagaDTO) {
        Vaga novaVaga = new Vaga();
        novaVaga.setTituloVaga(vagaDTO.getTituloVaga());
        novaVaga.setDataInicio(vagaDTO.getDataInicio());
        novaVaga.setDataExpiracao(vagaDTO.getDataExpiracao());
        novaVaga.setDescricaoVaga(vagaDTO.getDescricaoVaga());
        novaVaga.setSalario(vagaDTO.getSalario());
        novaVaga.setTotalDeCandidatosAplicado(vagaDTO.getTotalDeCandidatosAplicado());

        // Salvar a nova vaga
        novaVaga = vagaRepository.save(novaVaga);

        // Converter a vaga recém-criada para VagaDTO
        VagaDTO novaVagaDTO = convertToDTO(novaVaga);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaVagaDTO);
    }

    @GetMapping
    public ResponseEntity<List<VagaDTO>> listarVagas() {
        // Extrair CPF do token JWT
        String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar todas as vagas do banco de dados
        List<Vaga> vagas = vagaRepository.findAll();

        // Converter as vagas para DTOs
        List<VagaDTO> vagasDTO = vagas.stream().map(vaga -> {
            // Verificar se o candidato já se candidatou a esta vaga
            boolean candidatoJaCandidatou = vaga.getCandidatos().stream()
                    .anyMatch(candidato -> candidato.getCpf().equals(cpf));

            // Converter a vaga para DTO
            VagaDTO vagaDTO = convertToDTO(vaga);

            // Definir um atributo no DTO para indicar se o candidato já se candidatou a esta vaga
            vagaDTO.setCandidatoJaCandidatou(candidatoJaCandidatou);

            return vagaDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(vagasDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarVagaPorId(@PathVariable Long id) {
        try {
            Vaga vaga = vagaRepository.findById(id)
                    .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + id));
            VagaDTO vagaDTO = convertToDTO(vaga);
            return ResponseEntity.ok(vagaDTO);
        } catch (VagaNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Vaga não encontrada", "Vaga não encontrada com o ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Método para converter a entidade Vaga em um DTO VagaDTO
    private VagaDTO convertToDTO(Vaga vaga) {
        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setId(vaga.getId());
        vagaDTO.setTituloVaga(vaga.getTituloVaga());
        vagaDTO.setDataInicio(vaga.getDataInicio());
        vagaDTO.setDataExpiracao(vaga.getDataExpiracao());
        vagaDTO.setDescricaoVaga(vaga.getDescricaoVaga());
        vagaDTO.setSalario(vaga.getSalario());
        vagaDTO.setTotalDeCandidatosAplicado(vaga.getTotalDeCandidatosAplicado());
        return vagaDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizarVaga(@PathVariable Long id, @RequestBody Vaga vagaAtualizada) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new VagaNotFoundException("Vaga não encontrada com o ID: " + id));

        vaga.setTituloVaga(vagaAtualizada.getTituloVaga());
        vaga.setDataInicio(vagaAtualizada.getDataInicio());
        vaga.setDataExpiracao(vagaAtualizada.getDataExpiracao());
        vaga.setDescricaoVaga(vagaAtualizada.getDescricaoVaga());
        vaga.setSalario(vagaAtualizada.getSalario());
        vaga.setTotalDeCandidatosAplicado(vagaAtualizada.getTotalDeCandidatosAplicado());

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
    public ResponseEntity<String> candidatarAVaga(@PathVariable Long vagaId) {
        // Extrair CPF do token JWT
        String cpf = SecurityContextHolder.getContext().getAuthentication().getName();

        String mensagem = vagaService.candidatarAVaga(vagaId, cpf);
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/candidato/{id}")
    public ResponseEntity<List<VagaDTO>> listarVagasDoUsuario(@PathVariable Long id) {
        // Buscar candidato pelo ID do usuário
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new CandidatoNotFoundException("Candidato não encontrado com o ID: " + id));

        // Extrair vagas associadas ao candidato
        List<Vaga> vagasDoUsuario = candidato.getVagas();

        // Converter as vagas para DTOs
        List<VagaDTO> vagasDTO = vagasDoUsuario.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(vagasDTO);
    }
}