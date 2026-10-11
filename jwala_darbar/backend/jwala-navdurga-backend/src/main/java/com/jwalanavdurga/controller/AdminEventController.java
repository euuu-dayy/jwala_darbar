package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.AdminEventRequest;
import com.jwalanavdurga.dto.AdminEventResponse;
import com.jwalanavdurga.service.AdminEventService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    public AdminEventController(
            AdminEventService adminEventService) {

        this.adminEventService = adminEventService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EVENT_VIEW')")
    public ResponseEntity<List<AdminEventResponse>> getAllEvents() {

        return ResponseEntity.ok(
                adminEventService.getAllEvents()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_VIEW')")
    public ResponseEntity<AdminEventResponse> getEvent(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminEventService.getEvent(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EVENT_CREATE')")
    public ResponseEntity<AdminEventResponse> createEvent(
            @Valid @RequestBody AdminEventRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        adminEventService.createEvent(request)
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_UPDATE')")
    public ResponseEntity<AdminEventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody AdminEventRequest request) {

        return ResponseEntity.ok(
                adminEventService.updateEvent(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_DELETE')")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {

        adminEventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAuthority('EVENT_PUBLISH')")
    public ResponseEntity<AdminEventResponse> publishEvent(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                adminEventService.publishEvent(id)
        );
    }
}