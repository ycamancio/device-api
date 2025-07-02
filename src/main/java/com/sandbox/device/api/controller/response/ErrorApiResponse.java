package com.sandbox.device.api.controller.response;

import com.sandbox.device.api.enums.ErrorType;

public class ErrorApiResponse {

    private int id = 999;
    private ErrorType type;
    private String message;

    public ErrorApiResponse() {
    }

    public ErrorApiResponse(ErrorType type, String message) {
        this.type = type;
        this.message = message;
    }


    public ErrorApiResponse(int id, ErrorType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
