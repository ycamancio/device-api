package com.sandbox.device.api.controller;

import com.sandbox.device.api.annotation.LogEvent;
import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.controller.request.UpdateDeviceRequest;
import com.sandbox.device.api.controller.response.ResponseStatusExceptionModel;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.enums.DeviceState;
import com.sandbox.device.api.errorHandling.ApiError;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sandbox.device.api.constants.EventConstants.*;


@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Management", description = "All device related operations")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @LogEvent(id = CREATE_DEVICE_EVENT_ID, description = CREATE_DEVICE_EVENT_DESCRIPTION)
    @Operation(summary = "Create a new device", description = "Creates a new device with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data / business rule violation",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Device> create(@Valid @RequestBody CreateDeviceRequest request) throws DeviceBusinessRuleException {
        return new ResponseEntity<>(deviceService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @LogEvent(id = UPDATE_DEVICE_EVENT_ID, description = UPDATE_DEVICE_EVENT_DESCRIPTION)
    @Operation(summary = "Update an existing device", description = "Update an existing device with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data / business rule violation",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ResponseStatusExceptionModel.class)))
    })
    public ResponseEntity<Device> update(@PathVariable Integer id, @Valid @RequestBody UpdateDeviceRequest request) throws DeviceBusinessRuleException {
        return new ResponseEntity<>(deviceService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @LogEvent(id = FIND_DEVICE_BY_ID_EVENT_ID, description = FIND_DEVICE_BY_ID_EVENT_DESCRIPTION)
    @Operation(summary = "Retrieve device by id", description = "Fetch an existing device by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device exists and returned successfully",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ResponseStatusExceptionModel.class)))
    })
    public ResponseEntity<Device> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(deviceService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @LogEvent(id = FIND_ALL_DEVICES_EVENT_ID, description = FIND_ALL_DEVICES_EVENT_DESCRIPTION)
    @Operation(summary = "Retrieve all devices", description = "Fetch all devices or filter by name/brand using query parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Device.class)))
    })
    public ResponseEntity<List<Device>> findAll(){
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping(params = "state")
    @LogEvent(id = FIND_DEVICE_BY_NAME_EVENT_ID, description = FIND_DEVICE_BY_NAME_EVENT_DESCRIPTION)
    public ResponseEntity<List<Device>> findByState(@RequestParam @Parameter(description = "Filter devices by state") DeviceState state) {
        return ResponseEntity.ok(deviceService.findByState(state));
    }

    @GetMapping(params = "brand")
    @LogEvent(id = FIND_DEVICE_BY_BRAND_EVENT_ID, description = FIND_DEVICE_BY_BRAND_EVENT_DESCRIPTION)
    public ResponseEntity<List<Device>> findByBrand(@RequestParam @Parameter(description = "Filter devices by brand") String brand) {
        return ResponseEntity.ok(deviceService.findByBrand(brand));
    }

    @DeleteMapping("/{id}")
    @LogEvent(id = DELETE_DEVICE_EVENT_ID, description = DELETE_DEVICE_EVENT_DESCRIPTION)
    @Operation(summary = "Delete a device", description = "Deletes an existing device by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device deleted successfully"),
            @ApiResponse(responseCode = "400", description = "business rule violation - if device is in use",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> delete(@PathVariable Integer id) throws DeviceBusinessRuleException {
        deviceService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
