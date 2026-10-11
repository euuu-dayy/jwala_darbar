package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.AuthResponse;
import com.jwalanavdurga.dto.LoginRequest;
import com.jwalanavdurga.security.CustomUserDetails;
import com.jwalanavdurga.security.JwtService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(
                userDetails.getUserId(),
                userDetails.getUsername()
        );

        AuthResponse response = new AuthResponse(
                token,
                "Bearer",
                userDetails.getUserId(),
                userDetails.getUsername()
        );

        return ResponseEntity.ok(response);
    }
}