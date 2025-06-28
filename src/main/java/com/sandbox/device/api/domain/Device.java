package com.sandbox.device.api.domain;

import com.sandbox.device.api.enums.DeviceState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
