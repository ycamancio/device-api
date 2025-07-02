package com.sandbox.device.api.domain;

import com.sandbox.device.api.enums.AuditAction;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditAction action;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String username;

    private Timestamp createdAt;

    public Audit() {
    }

    public Audit(AuditAction action, String description, String username) {
        this.action = action;
        this.description = description;
        this.username = username;
    }


    @PrePersist
    private void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
