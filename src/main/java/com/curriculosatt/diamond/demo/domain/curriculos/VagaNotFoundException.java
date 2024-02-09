package com.curriculosatt.diamond.demo.domain.curriculos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VagaNotFoundException extends RuntimeException {

    public VagaNotFoundException(String message) {
        super(message);
    }
}
