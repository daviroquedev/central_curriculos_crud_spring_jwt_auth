package com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class VagaNotFoundException extends RuntimeException {

    public VagaNotFoundException(String message) {
        super(message);
    }
}
