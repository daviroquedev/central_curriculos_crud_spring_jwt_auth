package com.curriculosatt.diamond.demo.domain.curriculos;


import com.curriculosatt.diamond.demo.domain.curriculos.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

}
