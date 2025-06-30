package com.sandbox.device.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceState {
    AVAILABLE, IN_USE, INACTIVE;

    @JsonCreator
    public static DeviceState fromString(String value) {
        for (DeviceState state : DeviceState.values()) {
            if (state.name().equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown device state: " + value);
    }
}
