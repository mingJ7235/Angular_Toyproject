package com.rest.angular_api.exception;

public class CUserNotFound extends RuntimeException{
    public CUserNotFound (String msg, Throwable t) {
        super(msg, t);
    }

    public CUserNotFound (String msg){
        super(msg);
    }

    public CUserNotFound () {
        super();
    }
}
