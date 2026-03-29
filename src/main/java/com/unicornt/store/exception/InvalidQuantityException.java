package com.unicornt.store.exception;

public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(int qty) {
        super("Cantidad invalida: " + qty + ". Debe ser un entero mayor a 0.");
    }

    public InvalidQuantityException(String message) {
        super(message);
    }
}
