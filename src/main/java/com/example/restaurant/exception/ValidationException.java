package com.example.restaurant.exception;

/**
 * Exceção lançada quando há erro de validação de dados de entrada.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationException(String message) {
        super(message);
    }
}
