package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.AdminFestivalRequest;
import com.jwalanavdurga.dto.AdminFestivalResponse;
import com.jwalanavdurga.service.AdminFestivalService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/festivals")
public class AdminFestivalController {

    private final AdminFestivalService adminFestivalService;

    public AdminFestivalController(
            AdminFestivalService adminFestivalService) {

        this.adminFestivalService = adminFestivalService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FESTIVAL_VIEW')")
    public ResponseEntity<List<AdminFestivalResponse>>
    getAllFestivals() {

        return ResponseEntity.ok(
                adminFestivalService.getAllFestivals()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('FESTIVAL_VIEW')")
    public ResponseEntity<AdminFestivalResponse>
    getFestival(@PathVariable Long id) {

        return ResponseEntity.ok(
                adminFestivalService.getFestival(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FESTIVAL_CREATE')")
    public ResponseEntity<AdminFestivalResponse>
    createFestival(
            @Valid @RequestBody AdminFestivalRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        adminFestivalService
                                .createFestival(request)
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<AdminFestivalResponse>
    updateFestival(
            @PathVariable Long id,
            @Valid @RequestBody AdminFestivalRequest request) {

        return ResponseEntity.ok(
                adminFestivalService.updateFestival(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('FESTIVAL_DELETE')")
    public ResponseEntity<Void> deleteFestival(
            @PathVariable Long id) {

        adminFestivalService.deleteFestival(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<AdminFestivalResponse>
    publishFestival(@PathVariable Long id) {

        return ResponseEntity.ok(
                adminFestivalService.publishFestival(id)
        );
    }
}