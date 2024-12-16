package com.trabalho.bicicletario.config;

import com.trabalho.bicicletario.dto.ErroWrapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroWrapper> entidadeNaoEncontrada(EntityNotFoundException ex) {
        ErroWrapper erro = new ErroWrapper(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroWrapper> entidadeInvalida(IllegalArgumentException ex) {
        ErroWrapper erro = new ErroWrapper(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.ok(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroWrapper> erroGenerico(Exception ex) {
        ErroWrapper erro = new ErroWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
