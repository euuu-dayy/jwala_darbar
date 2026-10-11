package com.jwalanavdurga.controller.admin;

import com.jwalanavdurga.dto.admin.AdminAnnouncementRequest;
import com.jwalanavdurga.dto.admin.AdminAnnouncementResponse;
import com.jwalanavdurga.service.admin.AdminAnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/announcements")
@RequiredArgsConstructor
public class AdminAnnouncementController {

    private final AdminAnnouncementService announcementService;

    @GetMapping
    @PreAuthorize("hasAuthority('CONTENT_VIEW')")
    public ResponseEntity<List<AdminAnnouncementResponse>> getAll() {

        return ResponseEntity.ok(
                announcementService.getAll()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CONTENT_VIEW')")
    public ResponseEntity<AdminAnnouncementResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                announcementService.getById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CONTENT_CREATE')")
    public ResponseEntity<AdminAnnouncementResponse> create(
            @Valid @RequestBody AdminAnnouncementRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        announcementService.create(request)
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CONTENT_UPDATE')")
    public ResponseEntity<AdminAnnouncementResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminAnnouncementRequest request) {

        return ResponseEntity.ok(
                announcementService.update(id, request)
        );
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAuthority('CONTENT_PUBLISH')")
    public ResponseEntity<AdminAnnouncementResponse> publish(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                announcementService.publish(id)
        );
    }

    @PatchMapping("/{id}/unpublish")
    @PreAuthorize("hasAuthority('CONTENT_PUBLISH')")
    public ResponseEntity<AdminAnnouncementResponse> unpublish(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                announcementService.unpublish(id)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CONTENT_DELETE')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        announcementService.delete(id);

        return ResponseEntity.noContent().build();
    }
}