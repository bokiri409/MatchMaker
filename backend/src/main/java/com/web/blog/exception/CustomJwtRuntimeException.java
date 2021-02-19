package com.web.blog.exception;

public class CustomJwtRuntimeException extends RuntimeException {

    public CustomJwtRuntimeException(){
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomJwtRuntimeException(Exception ex){
        super(ex);
    }
}
