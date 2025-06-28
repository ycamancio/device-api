package com.sandbox.device.api.controller;

import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.service.DeviceService;
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
    public Device create(){
        return deviceService.create();
    }

    @GetMapping("/{id}")
    public Device findById(Integer id) {
        return deviceService.findById(id);
    }

    @GetMapping
    public List<Device> findAll() {
        return deviceService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        deviceService.delete(id);
    }
}
