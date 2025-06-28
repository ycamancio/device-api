package com.sandbox.device.api.controller;

import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(DeviceBusinessRuleException.class)
    public ResponseEntity<?> handleDeviceBusinessRuleException(DeviceBusinessRuleException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
