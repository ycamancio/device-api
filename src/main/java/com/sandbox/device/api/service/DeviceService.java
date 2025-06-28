package com.sandbox.device.api.service;

import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.enums.DeviceState;
import com.sandbox.device.api.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device create(CreateDeviceRequest request) throws DeviceBusinessRuleException {

        if(deviceRepository.existsByNameAndBrand(request.name(), request.brand())) {
            throw new DeviceBusinessRuleException("Device with the same name and brand already exists.");
        }

        Device device = new Device(request.name(), request.brand(), DeviceState.AVAILABLE);
        return deviceRepository.save(device);
    }


    public Device findById(Integer id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device update(Device device) {
        return deviceRepository.save(device);
    }

    public void delete(Integer id) throws DeviceBusinessRuleException {

        Device device = findById(id);
        if (device.getState() == DeviceState.IN_USE){
            throw new DeviceBusinessRuleException("Cannot delete a device that is currently in use.");
        }

        deviceRepository.deleteById(id);
    }
}
