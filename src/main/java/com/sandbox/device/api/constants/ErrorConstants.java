package com.sandbox.device.api.constants;

public class ErrorConstants {

    //Device combo refers to the combination of brand and name of a device.

    public static final int REQUEST_DEFAULT_VALIDATION_ERROR_CODE = 1000;

    public static final int UPDATE_REQUEST_NEEDS_AT_LEAST_ONE_FIELD_CODE = 1001;
    public static final String UPDATE_REQUEST_NEEDS_AT_LEAST_ONE_FIELD_MESSAGE = "At least one field must be provided for update.";

    public static final int DEVICE_COMBO_ALREADY_EXISTS_CODE = 1010;
    public static final String DEVICE_COMBO_ALREADY_EXISTS_CODE_MESSAGE = "Device already exists with the same brand and name combination.";

    public static final int DEVICE_COMBO_MUST_BE_UNIQUE_CODE = 1020;
    public static final String DEVICE_COMBO_MUST_BE_UNIQUE_MESSAGE = "Device with the same name and brand already exists and this combination must be unique.";

    public static final int DEVICE_IN_USE_CAN_NOT_BE_DELETED_CODE = 1030;
    public static final String DEVICE_IN_USE_CAN_NOT_BE_DELETED_MESSAGE = "Device is in use and cannot be deleted.";
}
