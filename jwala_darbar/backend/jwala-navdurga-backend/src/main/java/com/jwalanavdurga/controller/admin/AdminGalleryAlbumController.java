package com.jwalanavdurga.controller.admin;

import com.jwalanavdurga.dto.admin.AdminGalleryAlbumRequest;
import com.jwalanavdurga.dto.admin.AdminGalleryAlbumResponse;
import com.jwalanavdurga.service.admin.AdminGalleryAlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/gallery")
@RequiredArgsConstructor
public class AdminGalleryAlbumController {

    private final AdminGalleryAlbumService galleryService;

    @GetMapping
    @PreAuthorize("hasAuthority('GALLERY_VIEW')")
    public ResponseEntity<List<AdminGalleryAlbumResponse>> getAll() {
        return ResponseEntity.ok(
                galleryService.getAll()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GALLERY_VIEW')")
    public ResponseEntity<AdminGalleryAlbumResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                galleryService.getById(id)
        );
    }

    @GetMapping("/festival/{festivalId}")
    @PreAuthorize("hasAuthority('GALLERY_VIEW')")
    public ResponseEntity<List<AdminGalleryAlbumResponse>> getByFestival(
            @PathVariable Long festivalId) {

        return ResponseEntity.ok(
                galleryService.getByFestivalId(festivalId)
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('GALLERY_CREATE')")
    public ResponseEntity<AdminGalleryAlbumResponse> create(
            @Valid @RequestBody AdminGalleryAlbumRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(galleryService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('GALLERY_UPDATE')")
    public ResponseEntity<AdminGalleryAlbumResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminGalleryAlbumRequest request) {

        return ResponseEntity.ok(
                galleryService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GALLERY_DELETE')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        galleryService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('GALLERY_UPDATE')")
    public ResponseEntity<AdminGalleryAlbumResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                galleryService.changeStatus(id, status)
        );
    }
}