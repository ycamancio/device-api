package com.sandbox.device.api.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandbox.device.api.enums.DeviceState;

public record UpdateDeviceRequest(String name, String brand, DeviceState state) {

    @JsonIgnore
    public boolean isBlank() {
        return (name.isBlank()) && (brand.isBlank()) && state == null;
    }
}
