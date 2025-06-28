package com.sandbox.device.api.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CreateDeviceRequest(@NotBlank (message = "Name is mandatory field. Every device needs a name") String name,
                                  @NotBlank(message = "Brand is a mandatory field. Every device needs a brand") String brand) {
}
