package com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions;

import com.curriculosatt.diamond.demo.domain.curriculos.infra.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        String mensagemErro = "Erro de desserialização JSON: Valor inválido para o campo '" + ex.getPath().get(0).getFieldName() +
                "'. O valor '" + ex.getValue() + "' não é aceito. Valores suportados são " + Arrays.toString(ex.getTargetType().getEnumConstants());
        return ResponseEntity.badRequest().body(new ErrorResponse(mensagemErro, "erro de desserialização1"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String mensagemErro =
                "Valores suportados para Escolaridade são [SUPERIOR, MEDIO, MESTRADO, FUNDAMENTAL, TECNICO, POS_GRADUACAO, DOUTORADO, DEFAULT]";
        return ResponseEntity.badRequest().body(new ErrorResponse(mensagemErro, "erro de desserialização"));
    }
}