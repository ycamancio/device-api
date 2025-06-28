package com.sandbox.device.api.service;

import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device create(){

        return null;
    }

    public Device findById(Integer id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device update(Device device) {
        return deviceRepository.save(device);
    }

    public void delete(Integer id) {
        deviceRepository.deleteById(id);
    }
}
