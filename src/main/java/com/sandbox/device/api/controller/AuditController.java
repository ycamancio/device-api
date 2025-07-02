package com.sandbox.device.api.controller;

import com.sandbox.device.api.annotation.LogEvent;
import com.sandbox.device.api.domain.Audit;
import com.sandbox.device.api.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sandbox.device.api.constants.EventConstants.FIND_ALL_AUDITS_EVENT_DESCRIPTION;
import static com.sandbox.device.api.constants.EventConstants.FIND_ALL_AUDITS_EVENT_ID;

@RestController
@RequestMapping("/api/audits")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @LogEvent(id = FIND_ALL_AUDITS_EVENT_ID, description = FIND_ALL_AUDITS_EVENT_DESCRIPTION)
    public ResponseEntity<List<Audit>> findAll() {
        return ResponseEntity.ok(auditService.findAll());
    }

}
