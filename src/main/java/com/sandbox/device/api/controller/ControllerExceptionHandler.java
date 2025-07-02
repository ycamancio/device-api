package com.sandbox.device.api.controller;

import com.sandbox.device.api.controller.response.ErrorApiResponse;
import com.sandbox.device.api.enums.ErrorType;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(DeviceBusinessRuleException.class)
    public ResponseEntity<?> handleDeviceBusinessRuleException(DeviceBusinessRuleException e) {
        ErrorApiResponse errorApiResponse = new ErrorApiResponse(ErrorType.BUSINESS_RULE, e.getMessage());
        return new ResponseEntity<>(errorApiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ErrorApiResponse> errorList = new ArrayList<>();
        for(FieldError error : e.getFieldErrors()){
            errorList.add(new ErrorApiResponse(ErrorType.INVALID_REQUEST, error.getDefaultMessage()));
        }
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }
}
