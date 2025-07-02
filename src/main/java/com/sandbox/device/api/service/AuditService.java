package com.sandbox.device.api.service;

import com.sandbox.device.api.domain.Audit;
import com.sandbox.device.api.enums.AuditAction;
import com.sandbox.device.api.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public Audit auditAction(AuditAction action, String description, String username) {
        Audit audit = new Audit(action, description, username);
        return auditRepository.save(audit);
    }

    public List<Audit> findAll() {
        return auditRepository.findAll();
    }
}
