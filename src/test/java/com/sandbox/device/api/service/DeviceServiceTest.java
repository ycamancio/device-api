package com.sandbox.device.api.service;

import com.sandbox.device.api.controller.request.CreateDeviceRequest;
import com.sandbox.device.api.controller.request.UpdateDeviceRequest;
import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.enums.DeviceState;
import com.sandbox.device.api.exception.DeviceBusinessRuleException;
import com.sandbox.device.api.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void when_create_and_deviceWithSameNameAndBrandExists_then_throwDeviceBusinessRuleException() {

        CreateDeviceRequest request = new CreateDeviceRequest("Device1", "Brand1");

        when(deviceRepository.existsByNameAndBrand(request.name(), request.brand())).thenReturn(true);

        assertThrows(DeviceBusinessRuleException.class, () -> deviceService.create(request));
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    void when_create_and_deviceWithSameNameAndBrandDoesNotExist_then_createDevice() throws DeviceBusinessRuleException {

        CreateDeviceRequest request = new CreateDeviceRequest("Device1", "Brand1");
        Device mockSavedDevice = new Device(request.name(), request.brand(), DeviceState.AVAILABLE);

        when(deviceRepository.existsByNameAndBrand(request.name(), request.brand())).thenReturn(false);
        when(deviceRepository.save(any(Device.class))).thenReturn(mockSavedDevice);

        Device result = deviceService.create(request);
        verify(deviceRepository).save(any());

        assertEquals(mockSavedDevice.getName(), result.getName());
        assertEquals(mockSavedDevice.getBrand(), result.getBrand());
    }


    @Test
    void when_update_and_deviceDoesNotExist_then_throwResponseStatusException() {

        UpdateDeviceRequest updateRequest = new UpdateDeviceRequest("Device1", "Brand1", DeviceState.AVAILABLE);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> deviceService.update(1, updateRequest));
        verify(deviceRepository, never()).save(any(Device.class));
    }


    @Test
    void when_update_and_deviceExists_and_requestIsInvalid_then_throwDeviceBusinessRuleException() {

        UpdateDeviceRequest updateRequest = new UpdateDeviceRequest("", "", null);
        Device existingDevice = new Device("Device1", "Brand1", DeviceState.AVAILABLE);
        existingDevice.setId(1);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(existingDevice));

        assertThrows(DeviceBusinessRuleException.class, () -> deviceService.update(1, updateRequest));
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    void when_update_and_deviceExists_and_requestIsValid_but_thereIsAnotherDeviceWithSameNameAndBrand_then_throwDeviceBusinessRuleException() {

        UpdateDeviceRequest updateRequest = new UpdateDeviceRequest("Device1", "Brand1", DeviceState.AVAILABLE);
        Device existingDevice = new Device("Device1", "Brand1", DeviceState.AVAILABLE);
        existingDevice.setId(1);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.existsByNameAndBrandAndIdNot("Device1", "Brand1", 1)).thenReturn(true);

        assertThrows(DeviceBusinessRuleException.class, () -> deviceService.update(1, updateRequest));
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    void when_update_and_requestIsValid_and_deviceExists_and_noOtherDeviceWithSameNameAndBrand_then_updateDevice() throws DeviceBusinessRuleException {

        UpdateDeviceRequest updateRequest = new UpdateDeviceRequest("New name", "Another brand", DeviceState.IN_USE);
        Device existingDevice = new Device("Device 1", "Brand 1", DeviceState.AVAILABLE);
        existingDevice.setId(1);

        Device expectedUpdatedDevice = new Device("New name", "Another brand", DeviceState.IN_USE);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.existsByNameAndBrandAndIdNot(anyString(), anyString(), anyInt())).thenReturn(false);
        when(deviceRepository.save(any(Device.class))).thenReturn(expectedUpdatedDevice);

        Device result = deviceService.update(1, updateRequest);
        verify(deviceRepository).save(any());

        assertEquals(expectedUpdatedDevice.getName(), result.getName());
        assertEquals(expectedUpdatedDevice.getBrand(), result.getBrand());
        assertEquals(expectedUpdatedDevice.getState(), result.getState());
    }

    @Test
    void when_findById_and_deviceDoesntExist_then_throwResponseStatusException() {

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> deviceService.findById(1));
    }

    @Test
    void when_findById_and_deviceExists_then_returnDevice() {

        Device mockDevice = new Device("Device1", "Brand1", DeviceState.AVAILABLE);
        mockDevice.setId(1);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(mockDevice));

        Device result = deviceService.findById(1);

        assertEquals(mockDevice.getName(), result.getName());
        assertEquals(mockDevice.getBrand(), result.getBrand());
        assertEquals(mockDevice.getState(), result.getState());
    }

    @Test
    void when_findByName_and_noDevicesFound_then_throwResponseStatusException() {

        when(deviceRepository.findByName(anyString())).thenReturn(List.of());

        assertThrows(ResponseStatusException.class, () -> deviceService.findByName("NonExistentDevice"));
    }

    @Test
    void when_findByName_and_devicesFound_then_returnDevices() {

        String searchParameter = "Device1";
        Device mockDevice1 = new Device("Device1", "Brand1", DeviceState.AVAILABLE);
        Device mockDevice2 = new Device("Device1", "Brand2", DeviceState.IN_USE);

        when(deviceRepository.findByName(anyString())).thenReturn(List.of(mockDevice1, mockDevice2));

        List<Device> result = deviceService.findByName(searchParameter);

        assertEquals(2, result.size());
        assertEquals(searchParameter, result.get(0).getName());
        assertEquals(searchParameter, result.get(1).getName());
    }

    @Test
    void when_findByBrand_and_noDevicesFound_then_throwResponseStatusException() {

        when(deviceRepository.findByBrand(anyString())).thenReturn(List.of());

        assertThrows(ResponseStatusException.class, () -> deviceService.findByBrand("NonExistentBrand"));
    }

    @Test
    void when_findByBrand_and_devicesFound_then_returnDevices() {

        String searchParameter = "Brand1";
        Device mockDevice1 = new Device("Device1", "Brand1", DeviceState.AVAILABLE);
        Device mockDevice2 = new Device("Device2", "Brand1", DeviceState.IN_USE);

        when(deviceRepository.findByBrand(anyString())).thenReturn(List.of(mockDevice1, mockDevice2));

        List<Device> result = deviceService.findByBrand(searchParameter);

        assertEquals(2, result.size());
        assertEquals(searchParameter, result.get(0).getBrand());
        assertEquals(searchParameter, result.get(1).getBrand());
    }

    @Test
    void when_findAll_and_thereAreNoDevices_then_returnEmptyList() {

        when(deviceRepository.findAll()).thenReturn(List.of());

        List<Device> result = deviceService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void when_findAll_and_thereAreDevices_then_returnListOfDevices() {

        Device mockDevice1 = new Device("Device1", "Brand1", DeviceState.AVAILABLE);
        Device mockDevice2 = new Device("Device2", "Brand2", DeviceState.IN_USE);

        when(deviceRepository.findAll()).thenReturn(List.of(mockDevice1, mockDevice2));

        List<Device> result = deviceService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void when_deleteById_and_deviceDoesNotExist_then_throwResponseStatusException() {

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> deviceService.deleteById(1));
        verify(deviceRepository, never()).deleteById(anyInt());
    }

    @Test
    void when_deleteById_and_deviceIsInUse_then_throwDeviceBusinessRuleException() {

        Device mockDevice = new Device("Device1", "Brand1", DeviceState.IN_USE);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(mockDevice));

        assertThrows(DeviceBusinessRuleException.class, () -> deviceService.deleteById(1));
        verify(deviceRepository, never()).deleteById(anyInt());
    }

    @ParameterizedTest
    @EnumSource(value = DeviceState.class, names = {"AVAILABLE", "INACTIVE"})
    void when_deleteById_and_deviceIsNotInUse_then_deleteDevice(DeviceState state) throws DeviceBusinessRuleException {

        Device mockDevice = new Device("Device1", "Brand1", state);

        when(deviceRepository.findById(anyInt())).thenReturn(Optional.of(mockDevice));

        deviceService.deleteById(1);

        verify(deviceRepository).deleteById(1);
    }
}
