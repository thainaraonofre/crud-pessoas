package com.examplo.crudpessoas.exception;

public class CPFInvalido extends RuntimeException {
    public CPFInvalido(String message) {
        super(message);
    }
}
