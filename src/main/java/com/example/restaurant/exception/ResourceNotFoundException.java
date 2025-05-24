package com.example.restaurant.exception;

/**
 * Exceção lançada quando um recurso não é encontrado na base de dados.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
