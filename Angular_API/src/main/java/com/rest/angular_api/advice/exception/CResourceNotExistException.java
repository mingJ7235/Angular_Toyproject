package com.rest.angular_api.advice.exception;

public class CResourceNotExistException extends RuntimeException{

    public CResourceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CResourceNotExistException(String message) {
        super(message);
    }

    public CResourceNotExistException() {
        super();
    }

}
