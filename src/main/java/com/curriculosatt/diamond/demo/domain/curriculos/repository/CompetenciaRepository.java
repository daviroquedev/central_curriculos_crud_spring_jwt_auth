package com.curriculosatt.diamond.demo.domain.curriculos.repository;

import com.curriculosatt.diamond.demo.domain.curriculos.entity.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
