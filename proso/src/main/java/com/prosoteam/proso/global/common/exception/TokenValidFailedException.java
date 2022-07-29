package com.prosoteam.proso.global.common.exception;


import com.prosoteam.proso.global.common.ErrorCode;
import lombok.Getter;

@Getter
public class TokenValidFailedException extends RuntimeException {

    private ErrorCode errorCode;
    public TokenValidFailedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }

    public TokenValidFailedException(String message) {
        super(message);
    }
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }

}