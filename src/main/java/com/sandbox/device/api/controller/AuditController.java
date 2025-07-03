package com.sandbox.device.api.controller;

import com.sandbox.device.api.annotation.LogEvent;
import com.sandbox.device.api.domain.Audit;
import com.sandbox.device.api.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sandbox.device.api.constants.EventConstants.FIND_ALL_AUDITS_EVENT_DESCRIPTION;
import static com.sandbox.device.api.constants.EventConstants.FIND_ALL_AUDITS_EVENT_ID;

@RestController
@RequestMapping("/api/audits")
@Tag(name = "Audit Management", description = "All audit related operations")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @LogEvent(id = FIND_ALL_AUDITS_EVENT_ID, description = FIND_ALL_AUDITS_EVENT_DESCRIPTION)
    @Operation(summary = "Retrieve all auditable actions", description = "Fetch all auditable actions performed in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Audit.class)))
    })
    public ResponseEntity<List<Audit>> findAll() {
        return ResponseEntity.ok(auditService.findAll());
    }

}
