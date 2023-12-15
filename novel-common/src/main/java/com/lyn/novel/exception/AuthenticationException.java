package com.lyn.novel.exception;

import com.lyn.novel.constant.ResponseCode;

public class AuthenticationException extends BaseExcetion{
    public AuthenticationException(ResponseCode responseCode, String message) {
        super(responseCode, message);
    }

    public AuthenticationException(ResponseCode responseCode, String message, Throwable throwable) {
        super(responseCode, message, throwable);
    }
}
