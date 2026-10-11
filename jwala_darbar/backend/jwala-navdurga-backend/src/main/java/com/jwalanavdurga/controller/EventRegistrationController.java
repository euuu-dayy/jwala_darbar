package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.EventRegistrationRequest;
import com.jwalanavdurga.dto.EventRegistrationResponse;
import com.jwalanavdurga.service.EventRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/events")
public class EventRegistrationController {

    private final EventRegistrationService registrationService;

    public EventRegistrationController(
            EventRegistrationService registrationService) {

        this.registrationService = registrationService;
    }

    @PostMapping("/{eventId}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public EventRegistrationResponse register(
            @PathVariable Long eventId,
            @Valid @RequestBody EventRegistrationRequest request) {

        return registrationService.register(
                eventId,
                request
        );
    }
}