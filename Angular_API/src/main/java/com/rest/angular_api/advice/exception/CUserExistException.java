package com.rest.angular_api.advice.exception;

public class CUserExistException extends RuntimeException{

    public CUserExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CUserExistException(String message) {
        super(message);
    }

    public CUserExistException() {
        super();
    }

}
