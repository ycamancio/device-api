package com.sandbox.device.api.controller;

import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.controller.request.UpdateDeviceRequest;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Device> create(@Valid @RequestBody CreateDeviceRequest request) throws DeviceBusinessRuleException {
        return new ResponseEntity<>(deviceService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> update(@PathVariable Integer id, @Valid @RequestBody UpdateDeviceRequest request) throws DeviceBusinessRuleException {
        return new ResponseEntity<>(deviceService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(deviceService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Device>> findAll(){
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Device>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(deviceService.findByName(name));
    }

    @GetMapping(params = "brand")
    public ResponseEntity<List<Device>> findByBrand(@RequestParam String brand) {
        return ResponseEntity.ok(deviceService.findByBrand(brand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws DeviceBusinessRuleException {
        deviceService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
