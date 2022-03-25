package com.game.backend.exception;

public class BackEndException extends Exception {

    private static final long serialVersionUID = 2345365634347123492L;

    public static final String GENERIC_ERROR_MESSAGE = "Something wrong happened.";

    public BackEndException() {
        super();
    }

    public BackEndException(String msg) {
        super(msg);
    }
}
