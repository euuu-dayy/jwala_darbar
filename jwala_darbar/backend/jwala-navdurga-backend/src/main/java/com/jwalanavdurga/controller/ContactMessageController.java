package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.ContactMessageRequest;
import com.jwalanavdurga.dto.ContactMessageResponse;
import com.jwalanavdurga.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/contact")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(
            ContactMessageService contactMessageService) {

        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactMessageResponse createMessage(
            @Valid @RequestBody ContactMessageRequest request) {

        return contactMessageService.createMessage(request);
    }
}