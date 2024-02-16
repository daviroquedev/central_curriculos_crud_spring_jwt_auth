package com.curriculosatt.diamond.demo.domain.curriculos.controllers;

import com.curriculosatt.diamond.demo.domain.curriculos.enums.CandidatoRole;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.AuthDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.LoginResponseDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.enums.Escolaridade;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.ErrorResponse;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.security.TokenService;
import com.curriculosatt.diamond.demo.domain.curriculos.repository.CandidatoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CandidatoRepository repository;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data) {
        try {
            var userCpf = new UsernamePasswordAuthenticationToken(data.cpf(), "");
            var auth = this.authenticationManager.authenticate(userCpf);
            var token = tokenService.generateToken((Candidato) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            // Tratar exceção de autenticação
            return handleAuthenticationException(e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CandidatoDTO data) {
        try {
            if (repository.findByCpf(data.cpf()) != null) {
                // Tratar CPF já cadastrado
                return ResponseEntity.badRequest().body(new ErrorResponse("CPF já cadastrado", "cpf em uso" ));
            }

            Escolaridade escolaridade = getEscolaridade(data);
            CandidatoRole role = getRole(data);

            Candidato newUser = buildCandidato(data, escolaridade, role);

            repository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            // Captura a exceção com a mensagem personalizada e inclui na resposta
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "argumento inválido"));
        } catch (HttpMessageNotReadableException ex) {
            // Tratar exceção de desserialização do JSON
            return handleJsonDeserializationException(ex);
        } catch (Exception e) {
            // Tratar outras exceções não tratadas
            return handleUnexpectedException(e);
        }
    }

    // Métodos auxiliares

    private Escolaridade getEscolaridade(CandidatoDTO data) {
        if (data.escolaridade() == null) {
            throw new IllegalArgumentException("É necessário fornecer uma escolaridade.");
        } else {
            try {
                return Escolaridade.fromString(data.escolaridade());
            } catch (IllegalArgumentException e) {
                // Captura a exceção de conversão inválida e lança uma nova exceção com a mensagem personalizada
                throw new IllegalArgumentException("A escolaridade deve ser DEFAULT, SUPERIOR, etc.");
            }
        }
    }

    private CandidatoRole getRole(CandidatoDTO data) {
        try {
            return CandidatoRole.fromString(String.valueOf(data.role()));
        } catch (IllegalArgumentException e) {
            // Se o papel não estiver nos valores permitidos, lançar uma resposta indicando que deve estar no enum
            throw new IllegalArgumentException("O papel (role) deve estar entre os valores permitidos.");
        }
    }

    private ResponseEntity handleAuthenticationException(AuthenticationException e) {
        // Lógica para tratamento de exceção de autenticação
        System.out.println("Erro ao autenticar");
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private ResponseEntity handleJsonDeserializationException(HttpMessageNotReadableException ex) {
        // Lógica para tratamento de exceção de desserialização JSON
        String mensagemErro = "Erro de desserialização JSON: " + ex.getMessage();
        System.out.println(mensagemErro);
        return ResponseEntity.badRequest().body(mensagemErro);
    }

    private ResponseEntity handleUnexpectedException(Exception e) {
        // Lógica para tratamento de outras exceções não tratadas
        String mensagemErro = "Erro inesperado: " + e.getMessage();
        System.out.println(mensagemErro);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
    }

    private Candidato buildCandidato(CandidatoDTO data, Escolaridade escolaridade, CandidatoRole role) {
        // Lógica para construir o objeto Candidato
        Candidato newUser = new Candidato();
        newUser.setNome(data.nome());
        newUser.setCpf(data.cpf());
        newUser.setEmail(data.email());
        newUser.setDataNascimento(data.dataNascimento() != null ? Date.valueOf(data.dataNascimento()) : null);
        newUser.setTelefone(data.telefone());
        newUser.setEscolaridade(escolaridade.toString());
        newUser.setFuncao(data.funcao());
        newUser.setListaCompetencias(data.listaCompetencias());
        newUser.setStatusSolicitacao(data.statusSolicitacao());
        newUser.setRole(role);

        return newUser;
    }
}
