package com.sandbox.device.api.controller;

import com.sandbox.device.api.annotation.LogEvent;
import com.sandbox.device.api.constants.EventConstants;
import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.controller.request.UpdateDeviceRequest;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sandbox.device.api.constants.EventConstants.*;


@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Management", description = "API for managing devices with CRUD operations")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    @LogEvent(id = CREATE_DEVICE_EVENT_ID, description = CREATE_DEVICE_EVENT_DESCRIPTION)
    @Operation(summary = "Create a new device", description = "Creates a new device with the provided information")
    public ResponseEntity<Device> create(@Valid @RequestBody CreateDeviceRequest request) throws DeviceBusinessRuleException {
        return new ResponseEntity<>(deviceService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @LogEvent(id = UPDATE_DEVICE_EVENT_ID, description = UPDATE_DEVICE_EVENT_DESCRIPTION)
    public ResponseEntity<Device> update(@PathVariable Integer id, @Valid @RequestBody UpdateDeviceRequest request) throws DeviceBusinessRuleException {
        return new ResponseEntity<>(deviceService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @LogEvent(id = FIND_DEVICE_BY_ID_EVENT_ID, description = FIND_DEVICE_BY_ID_EVENT_DESCRIPTION)
    public ResponseEntity<Device> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(deviceService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @LogEvent(id = FIND_ALL_DEVICES_EVENT_ID, description = FIND_ALL_DEVICES_EVENT_DESCRIPTION)
    public ResponseEntity<List<Device>> findAll(){
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping(params = "name")
    @LogEvent(id = FIND_DEVICE_BY_NAME_EVENT_ID, description = FIND_DEVICE_BY_NAME_EVENT_DESCRIPTION)
    public ResponseEntity<List<Device>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(deviceService.findByName(name));
    }

    @GetMapping(params = "brand")
    @LogEvent(id = FIND_DEVICE_BY_BRAND_EVENT_ID, description = FIND_DEVICE_BY_BRAND_EVENT_DESCRIPTION)
    public ResponseEntity<List<Device>> findByBrand(@RequestParam String brand) {
        return ResponseEntity.ok(deviceService.findByBrand(brand));
    }

    @DeleteMapping("/{id}")
    @LogEvent(id = DELETE_DEVICE_EVENT_ID, description = DELETE_DEVICE_EVENT_DESCRIPTION)
    public ResponseEntity<?> delete(@PathVariable Integer id) throws DeviceBusinessRuleException {
        deviceService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
