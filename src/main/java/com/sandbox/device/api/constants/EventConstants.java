package com.sandbox.device.api.constants;

public class EventConstants {

    public static final int CREATE_DEVICE_EVENT_ID = 7000;
    public static final String CREATE_DEVICE_EVENT_DESCRIPTION = "Creating a new device";

    public static final int UPDATE_DEVICE_EVENT_ID = 7001;
    public static final String UPDATE_DEVICE_EVENT_DESCRIPTION = "Updating an existing device";

    public static final int DELETE_DEVICE_EVENT_ID = 7002;
    public static final String DELETE_DEVICE_EVENT_DESCRIPTION = "Deleting a device";

    public static final int FIND_ALL_DEVICES_EVENT_ID = 7003;
    public static final String FIND_ALL_DEVICES_EVENT_DESCRIPTION = "Retrieving all devices";

    public static final int FIND_DEVICE_BY_ID_EVENT_ID = 7004;
    public static final String FIND_DEVICE_BY_ID_EVENT_DESCRIPTION = "Retrieving a device by its id";

    public static final int FIND_DEVICE_BY_NAME_EVENT_ID = 7005;
    public static final String FIND_DEVICE_BY_NAME_EVENT_DESCRIPTION = "Retrieving a device by its name";

    public static final int FIND_DEVICE_BY_BRAND_EVENT_ID = 7006;
    public static final String FIND_DEVICE_BY_BRAND_EVENT_DESCRIPTION = "Retrieving devices by their brand";

    public static final int FIND_ALL_AUDITS_EVENT_ID = 7007;
    public static final String FIND_ALL_AUDITS_EVENT_DESCRIPTION = "Retrieving all audit entries";
}
