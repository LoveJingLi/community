package com.miao.community_m.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private int code;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
    }
    public CustomizeException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
