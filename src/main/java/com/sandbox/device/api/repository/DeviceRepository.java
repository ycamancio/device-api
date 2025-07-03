package com.sandbox.device.api.repository;

import com.sandbox.device.api.domain.Device;
import com.sandbox.device.api.enums.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    boolean existsByNameAndBrand(String name, String brand);

    boolean existsByNameAndBrandAndIdNot(String name, String brand, Integer id);

    List<Device> findByState(DeviceState state);

    List<Device> findByBrand(String brand);
}
