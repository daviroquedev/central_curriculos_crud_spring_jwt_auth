package com.curriculosatt.diamond.demo.domain.curriculos.repository;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    Optional<Candidato> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByTelefone(String telefone);
    boolean existsByEmail(String email);

    UserDetails findByCpf(String cpf);
}
