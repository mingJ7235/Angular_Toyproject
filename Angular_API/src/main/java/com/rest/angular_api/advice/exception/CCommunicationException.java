package com.rest.angular_api.advice.exception;

public class CCommunicationException extends RuntimeException{

    public CCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CCommunicationException(String message) {
        super(message);
    }

    public CCommunicationException() {
        super();
    }

}
