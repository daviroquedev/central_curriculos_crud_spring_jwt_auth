package com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions;

public class CandidatoNotFoundException extends RuntimeException {

    public CandidatoNotFoundException(String message) {
        super(message);
    }
}