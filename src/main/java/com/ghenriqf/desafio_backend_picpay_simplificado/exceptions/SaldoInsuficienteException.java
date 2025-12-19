package com.ghenriqf.desafio_backend_picpay_simplificado.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
