package com.cinema.exceptions;

public class EmailNoValidoException extends RuntimeException {
    public EmailNoValidoException(String message) {
        super(message);
    }
}
