package com.sandbox.device.api.service;

import com.sandbox.device.api.controller.request.UpdateDeviceRequest;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.enums.DeviceState;
import com.sandbox.device.api.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    public Device update(Integer id, UpdateDeviceRequest updateRequest) throws DeviceBusinessRuleException {

        Device deviceToUpdate = findById(id);

        if(updateRequest.isBlank()) {
            throw new DeviceBusinessRuleException("At least one field must be provided for update.");
        }

        if(deviceRepository.existsByNameAndBrandAndIdNot(updateRequest.name(), updateRequest.brand(), id)) {
            throw new DeviceBusinessRuleException("Device with the same name and brand already exists and this combination must be unique");
        }

        if (updateRequest.name() != null) {
            deviceToUpdate.setName(updateRequest.name());
        }

        if (updateRequest.brand() != null) {
            deviceToUpdate.setBrand(updateRequest.brand());
        }

        if(updateRequest.state() != null) {
            deviceToUpdate.setState(updateRequest.state());
        }

        return deviceRepository.save(deviceToUpdate);
    }

    public Device findById(Integer id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));
    }

    public List<Device> findDevices(Optional<String> name, Optional<String> brand){

        if(name.isEmpty() && brand.isEmpty()) {
           return deviceRepository.findAll();
        }

        if(name.isPresent() && brand.isPresent()) {
            return deviceRepository.findByNameAndBrand(name.get(), brand.get());
        } else if (name.isPresent()) {
            return deviceRepository.findByName(name.get());
        } else{
            return deviceRepository.findByBrand(brand.get());
        }
    }

    public void delete(Integer id) throws DeviceBusinessRuleException {

        Device device = findById(id);
        if (device.getState() == DeviceState.IN_USE){
            throw new DeviceBusinessRuleException("Cannot delete a device that is currently in use.");
        }

        deviceRepository.deleteById(id);
    }
}
