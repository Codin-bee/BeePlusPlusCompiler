package com.codingbee.exceptions;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super("Unexpected error occurred: " + message);
    }

    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
