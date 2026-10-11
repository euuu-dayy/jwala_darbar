package com.jwalanavdurga.controller.admin;

import com.jwalanavdurga.dto.admin.AdminMahaprasadRequest;
import com.jwalanavdurga.dto.admin.AdminMahaprasadResponse;
import com.jwalanavdurga.service.admin.AdminMahaprasadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/mahaprasad")
@RequiredArgsConstructor
public class AdminMahaprasadController {

    private final AdminMahaprasadService mahaprasadService;

    // =========================================================
    // GET ALL
    // =========================================================

    @GetMapping
    @PreAuthorize("hasAuthority('MAHAPRASAD_VIEW')")
    public ResponseEntity<List<AdminMahaprasadResponse>> getAll() {

        return ResponseEntity.ok(
                mahaprasadService.getAll()
        );
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MAHAPRASAD_VIEW')")
    public ResponseEntity<AdminMahaprasadResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                mahaprasadService.getById(id)
        );
    }

    // =========================================================
    // GET BY FESTIVAL
    // =========================================================

    @GetMapping("/festival/{festivalId}")
    @PreAuthorize("hasAuthority('MAHAPRASAD_VIEW')")
    public ResponseEntity<List<AdminMahaprasadResponse>> getByFestival(
            @PathVariable Long festivalId
    ) {

        return ResponseEntity.ok(
                mahaprasadService.getByFestival(festivalId)
        );
    }

    // =========================================================
    // CREATE
    // =========================================================

    @PostMapping
    @PreAuthorize("hasAuthority('MAHAPRASAD_CREATE')")
    public ResponseEntity<AdminMahaprasadResponse> create(
            @Valid @RequestBody AdminMahaprasadRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mahaprasadService.create(request));
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MAHAPRASAD_UPDATE')")
    public ResponseEntity<AdminMahaprasadResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminMahaprasadRequest request
    ) {

        return ResponseEntity.ok(
                mahaprasadService.update(id, request)
        );
    }

    // =========================================================
    // ACTIVATE
    // =========================================================

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('MAHAPRASAD_UPDATE')")
    public ResponseEntity<AdminMahaprasadResponse> activate(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                mahaprasadService.activate(id)
        );
    }

    // =========================================================
    // DEACTIVATE
    // =========================================================

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('MAHAPRASAD_UPDATE')")
    public ResponseEntity<AdminMahaprasadResponse> deactivate(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                mahaprasadService.deactivate(id)
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MAHAPRASAD_UPDATE')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        mahaprasadService.delete(id);

        return ResponseEntity.noContent().build();
    }
}