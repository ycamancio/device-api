package com.sandbox.device.api.errorHandling;

import com.sandbox.device.api.enums.ErrorType;

public class ApiError {

    private int code;
    private String message;
    private ErrorType type;

    public ApiError() {
    }

    public ApiError(ErrorType type, int code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public ApiError(ErrorType type, String message) {
        this.code = ErrorConstants.REQUEST_DEFAULT_VALIDATION_ERROR_CODE;
        this.type = type;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
