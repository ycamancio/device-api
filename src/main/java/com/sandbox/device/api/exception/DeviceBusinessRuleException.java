package com.sandbox.device.api.exception;

import com.sandbox.device.api.errorHandling.ApiError;

public class DeviceBusinessRuleException extends Exception {

    private ApiError apiError;

    public DeviceBusinessRuleException(ApiError apiError) {
        this.apiError = apiError;
    }

    public DeviceBusinessRuleException(String message) {
        super(message);
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }
}
