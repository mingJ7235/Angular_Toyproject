package com.rest.angular_api.advice.exception;

public class CNotOwnerException extends RuntimeException{

    public CNotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CNotOwnerException(String message) {
        super(message);
    }

    public CNotOwnerException() {
        super();
    }

}
