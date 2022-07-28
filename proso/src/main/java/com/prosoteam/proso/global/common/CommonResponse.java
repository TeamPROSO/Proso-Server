package com.prosoteam.proso.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private boolean success;

    private T data;

    private Error error;

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, data, null);
    }

    public static <T> CommonResponse<T> error(ErrorCode errorCode) {
        return new CommonResponse<>(false , null, Error.of(errorCode));
    }

    public static <T> CommonResponse<T> error(ErrorCode errorCode, String message) {
        return new CommonResponse<>(false , null, Error.of(errorCode, message));
    }
}
