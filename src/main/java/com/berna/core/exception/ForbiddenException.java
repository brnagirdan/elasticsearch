package com.berna.core.exception;

import javax.persistence.PersistenceException;

public class ForbiddenException extends PersistenceException {

    public ForbiddenException(){
        super("The operation is forbidden");
    }

    public ForbiddenException(String message){
        super(message);
    }

    public ForbiddenException(String message,Throwable cause){
        super(message,cause);
    }

    public ForbiddenException(Throwable cause){
        super(cause);
    }

}
