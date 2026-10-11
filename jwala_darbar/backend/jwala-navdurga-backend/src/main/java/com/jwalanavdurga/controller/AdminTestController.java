package com.jwalanavdurga.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminTestController {

    @GetMapping("/test")
    public String test(Authentication authentication) {

        return "Authenticated successfully as: "
                + authentication.getName();
    }

    @GetMapping("/authorities")
    public List<String> authorities(
            Authentication authentication) {

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();
    }
    
    @GetMapping("/event-create-test")
    @PreAuthorize("hasAuthority('EVENT_CREATE')")
    public String eventCreateTest() {

        return "EVENT_CREATE permission granted";
    }
}