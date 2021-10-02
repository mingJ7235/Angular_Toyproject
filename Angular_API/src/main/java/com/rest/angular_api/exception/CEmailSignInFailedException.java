package com.rest.angular_api.exception;

public class CEmailSignInFailedException extends RuntimeException{

    public CEmailSignInFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CEmailSignInFailedException(String message) {
        super(message);
    }

    public CEmailSignInFailedException() {
        super();
    }

}
