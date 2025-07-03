package com.sandbox.device.api.service;

import com.sandbox.device.api.annotation.Auditable;
import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.controller.request.UpdateDeviceRequest;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.enums.AuditAction;
import com.sandbox.device.api.enums.DeviceState;
import com.sandbox.device.api.enums.ErrorType;
import com.sandbox.device.api.errorHandling.ApiError;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.sandbox.device.api.constants.AuditConstants.*;
import static com.sandbox.device.api.constants.ErrorConstants.*;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Auditable(action= AuditAction.CREATE, description = ACTION_CREATE_DEVICE_DESCRIPTION)
    public Device create(CreateDeviceRequest request) throws DeviceBusinessRuleException {

        if(deviceRepository.existsByNameAndBrand(request.name(), request.brand())) {
            createApiErrorAndThrowException(ErrorType.BUSINESS_RULE,
                    DEVICE_COMBO_ALREADY_EXISTS_CODE, DEVICE_COMBO_ALREADY_EXISTS_CODE_MESSAGE);
        }

        Device device = new Device(request.name(), request.brand(), DeviceState.AVAILABLE);
        return deviceRepository.save(device);
    }

    @Auditable(action = AuditAction.UPDATE, description = ACTION_UPDATING_DEVICE_DESCRIPTION)
    public Device update(Integer id, UpdateDeviceRequest updateRequest) throws DeviceBusinessRuleException {

        Device deviceToUpdate = findById(id);

        if(updateRequest.isBlank()) {
            createApiErrorAndThrowException(ErrorType.BUSINESS_RULE,
                    UPDATE_REQUEST_NEEDS_AT_LEAST_ONE_FIELD_CODE, UPDATE_REQUEST_NEEDS_AT_LEAST_ONE_FIELD_MESSAGE);
        }

        if(deviceRepository.existsByNameAndBrandAndIdNot(updateRequest.name(), updateRequest.brand(), id)) {
            createApiErrorAndThrowException(ErrorType.BUSINESS_RULE,
                    DEVICE_COMBO_MUST_BE_UNIQUE_CODE, DEVICE_COMBO_MUST_BE_UNIQUE_MESSAGE);
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

    public List<Device> findByState(DeviceState state) {
        return deviceRepository.findByState(state);
    }

    public List<Device> findByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Auditable(action = AuditAction.DELETE, description = ACTION_DELETING_DEVICE_DESCRIPTION)
    public void deleteById(Integer id) throws DeviceBusinessRuleException {

        Optional<Device> device = deviceRepository.findById(id);

        if(device.isEmpty()){
            return;
        }

        if (device.get().getState() == DeviceState.IN_USE){
            createApiErrorAndThrowException(ErrorType.BUSINESS_RULE,
                    DEVICE_IN_USE_CAN_NOT_BE_DELETED_CODE, DEVICE_IN_USE_CAN_NOT_BE_DELETED_MESSAGE);
        }

        deviceRepository.deleteById(id);
    }

    private void createApiErrorAndThrowException(ErrorType type, int code, String message) throws DeviceBusinessRuleException {
        ApiError error = new ApiError(type, code, message);
        throw new DeviceBusinessRuleException(error);
    }
}
