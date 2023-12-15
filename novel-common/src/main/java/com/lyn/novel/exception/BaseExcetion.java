package com.lyn.novel.exception;

import com.lyn.novel.constant.ResponseCode;

public class BaseExcetion extends RuntimeException{

    private final ResponseCode responseCode;

    public BaseExcetion(ResponseCode responseCode, String message){
        super(message);
        this.responseCode = responseCode;
    }

    public BaseExcetion(ResponseCode responseCode, String message, Throwable throwable){
        super(message, throwable);
        this.responseCode = responseCode;
    }

}
