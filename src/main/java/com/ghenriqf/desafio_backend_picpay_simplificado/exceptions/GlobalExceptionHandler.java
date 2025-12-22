package com.ghenriqf.desafio_backend_picpay_simplificado.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ExceptionResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(),HttpStatus.NOT_FOUND.value() ,LocalDateTime.now());

        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(AutorizacaoException.class)
    public ResponseEntity<ExceptionResponse> handleAutorizacao(AutorizacaoException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.FORBIDDEN.value(), LocalDateTime.now());

        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(ServicoExternoException.class)
    public ResponseEntity<ExceptionResponse> handleServico(ServicoExternoException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value(), LocalDateTime.now());

        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ExceptionResponse> handleSaldoInsuficiente(SaldoInsuficienteException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatusCode.valueOf(422).value(), LocalDateTime.now());

        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("Usuário já cadastrado", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }
}