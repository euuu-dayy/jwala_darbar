package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.AdminFestivalDayRequest;
import com.jwalanavdurga.dto.AdminFestivalDayResponse;
import com.jwalanavdurga.service.AdminFestivalDayService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/festivals/{festivalId}/days")
public class AdminFestivalDayController {

    private final AdminFestivalDayService adminFestivalDayService;

    public AdminFestivalDayController(
            AdminFestivalDayService adminFestivalDayService) {

        this.adminFestivalDayService =
                adminFestivalDayService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FESTIVAL_VIEW')")
    public ResponseEntity<List<AdminFestivalDayResponse>>
    getDays(@PathVariable Long festivalId) {

        return ResponseEntity.ok(
                adminFestivalDayService.getDays(festivalId)
        );
    }

    @GetMapping("/{dayId}")
    @PreAuthorize("hasAuthority('FESTIVAL_VIEW')")
    public ResponseEntity<AdminFestivalDayResponse>
    getDay(
            @PathVariable Long festivalId,
            @PathVariable Long dayId) {

        return ResponseEntity.ok(
                adminFestivalDayService.getDay(
                        festivalId,
                        dayId
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<AdminFestivalDayResponse>
    createDay(
            @PathVariable Long festivalId,
            @Valid @RequestBody AdminFestivalDayRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        adminFestivalDayService.createDay(
                                festivalId,
                                request
                        )
                );
    }

    @PutMapping("/{dayId}")
    @PreAuthorize("hasAuthority('FESTIVAL_UPDATE')")
    public ResponseEntity<AdminFestivalDayResponse>
    updateDay(
            @PathVariable Long festivalId,
            @PathVariable Long dayId,
            @Valid @RequestBody AdminFestivalDayRequest request) {

        return ResponseEntity.ok(
                adminFestivalDayService.updateDay(
                        festivalId,
                        dayId,
                        request
                )
        );
    }

    @DeleteMapping("/{dayId}")
    @PreAuthorize("hasAuthority('FESTIVAL_DELETE')")
    public ResponseEntity<Void> deleteDay(
            @PathVariable Long festivalId,
            @PathVariable Long dayId) {

        adminFestivalDayService.deleteDay(
                festivalId,
                dayId
        );

        return ResponseEntity.noContent().build();
    }
}