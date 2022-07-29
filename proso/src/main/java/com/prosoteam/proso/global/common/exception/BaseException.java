package com.prosoteam.proso.global.common.exception;

import com.prosoteam.proso.global.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
public class BaseException extends RuntimeException{
    private ErrorCode errorCode;
    public BaseException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}