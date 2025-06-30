package com.sandbox.device.api.domain;

import com.sandbox.device.api.enums.DeviceState;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "brand"}))
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String brand;

    @Enumerated(EnumType.STRING)
    private DeviceState state;

    private Timestamp createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    public Device() {
    }

    public Device(String name, String brand, DeviceState state) {
        this.name = name;
        this.brand = brand;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
