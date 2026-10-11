package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.VolunteerApplicationRequest;
import com.jwalanavdurga.dto.VolunteerApplicationResponse;
import com.jwalanavdurga.service.VolunteerApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/volunteers")
public class VolunteerApplicationController {

    private final VolunteerApplicationService volunteerApplicationService;

    public VolunteerApplicationController(
            VolunteerApplicationService volunteerApplicationService) {

        this.volunteerApplicationService =
                volunteerApplicationService;
    }

    @PostMapping("/festival/{festivalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public VolunteerApplicationResponse apply(
            @PathVariable Long festivalId,
            @Valid @RequestBody VolunteerApplicationRequest request) {

        return volunteerApplicationService.apply(
                festivalId,
                request
        );
    }

    @GetMapping("/festival/{festivalId}")
    public List<VolunteerApplicationResponse> getByFestival(
            @PathVariable Long festivalId) {

        return volunteerApplicationService
                .getByFestival(festivalId);
    }
}