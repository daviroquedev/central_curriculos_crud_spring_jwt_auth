package com.curriculosatt.diamond.demo.domain.curriculos.controllers;

import com.curriculosatt.diamond.demo.domain.curriculos.Candidatos.*;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.AuthDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.CandidatoDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.dto.LoginResponseDTO;
import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import com.curriculosatt.diamond.demo.domain.curriculos.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CandidatoRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data) {
        try {
            // Fornecendo uma senha vazia durante a autenticação
            var userCpf = new UsernamePasswordAuthenticationToken(data.cpf(), "");
            var auth = this.authenticationManager.authenticate(userCpf);
            var token = tokenService.generateToken((Candidato) auth.getPrincipal());
            System.out.println(userCpf);
            System.out.println(auth);
            System.out.println(token);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            System.out.println("Erro ao autenticar");
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CandidatoDTO data) {
        if (this.repository.findByCpf(data.cpf()) != null) {
            System.out.println("CPF já cadastrado");
            return ResponseEntity.badRequest().build();
        }

        // Convertendo a String role para o enum CandidatoRole
        CandidatoRole role = CandidatoRole.USER; // Define um valor padrão
        if (data.role() != null && !data.role().equals("")) {
            try {
                role = CandidatoRole.fromString(String.valueOf(data.role()));
            } catch (IllegalArgumentException e) {
                // Tratamento de erro se o valor da role não for válido
                return ResponseEntity.badRequest().build();
            }
        }

        Candidato newUser = new Candidato();
        newUser.setNome(data.nome());
        newUser.setCpf(data.cpf());
        newUser.setEmail(data.email());
        newUser.setDataNascimento(data.dataNascimento() != null ? Date.valueOf(data.dataNascimento()) : null);
        newUser.setTelefone(data.telefone());
        newUser.setEscolaridade(data.escolaridade());
        newUser.setFuncao(data.funcao());
        newUser.setListaCompetencias(data.listaCompetencias());
        newUser.setStatusSolicitacao(data.statusSolicitacao());
        newUser.setRole(role);

        repository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}