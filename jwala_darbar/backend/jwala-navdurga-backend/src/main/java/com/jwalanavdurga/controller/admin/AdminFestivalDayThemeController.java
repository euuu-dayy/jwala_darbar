package com.jwalanavdurga.controller.admin;

import com.jwalanavdurga.dto.admin.AdminFestivalDayThemeRequest;
import com.jwalanavdurga.dto.admin.AdminFestivalDayThemeResponse;
import com.jwalanavdurga.service.admin.AdminFestivalDayThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/festival-days")
@RequiredArgsConstructor
public class AdminFestivalDayThemeController {

    private final AdminFestivalDayThemeService themeService;

    @GetMapping("/{festivalDayId}/theme")
    @PreAuthorize("hasAuthority('FESTIVAL_VIEW')")
    public ResponseEntity<AdminFestivalDayThemeResponse> getTheme(
            @PathVariable Long festivalDayId) {

        return ResponseEntity.ok(
                themeService.getByFestivalDayId(festivalDayId)
        );
    }

    @PostMapping("/{festivalDayId}/theme")
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<AdminFestivalDayThemeResponse> createTheme(
            @PathVariable Long festivalDayId,
            @Valid @RequestBody AdminFestivalDayThemeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(themeService.create(festivalDayId, request));
    }

    @PutMapping("/{festivalDayId}/theme")
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<AdminFestivalDayThemeResponse> updateTheme(
            @PathVariable Long festivalDayId,
            @Valid @RequestBody AdminFestivalDayThemeRequest request) {

        return ResponseEntity.ok(
                themeService.update(festivalDayId, request)
        );
    }

    @DeleteMapping("/{festivalDayId}/theme")
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<Void> deleteTheme(
            @PathVariable Long festivalDayId) {

        themeService.delete(festivalDayId);

        return ResponseEntity.noContent().build();
    }
}