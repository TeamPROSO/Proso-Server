package com.prosoteam.proso.global.advice;

import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdvice {

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     * @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) response code를 405로 설정한다.
     * @return 이전에 공통 응답 포맷으로 만들었던 CommonResponse를 실패시 응답 데이터로 보내준다
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected CommonResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("HttpRequestMethodNotSupportedException",e);
        return CommonResponse.error(ErrorCode.METHOD_NOT_ALLOWED);
    }


}
