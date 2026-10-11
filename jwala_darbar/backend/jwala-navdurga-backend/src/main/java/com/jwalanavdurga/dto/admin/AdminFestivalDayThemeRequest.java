package com.jwalanavdurga.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFestivalDayThemeRequest {

    @NotBlank(message = "Primary color is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Primary color must be a valid HEX color"
    )
    private String primaryColor;

    @NotBlank(message = "Secondary color is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Secondary color must be a valid HEX color"
    )
    private String secondaryColor;

    @NotBlank(message = "Accent color is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Accent color must be a valid HEX color"
    )
    private String accentColor;

    @NotBlank(message = "Accent light color is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Accent light color must be a valid HEX color"
    )
    private String accentLightColor;

    @NotBlank(message = "Accent dark color is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Accent dark color must be a valid HEX color"
    )
    private String accentDarkColor;

    @NotBlank(message = "Glow color is required")
    @Pattern(
        regexp = "^#[0-9A-Fa-f]{6}$",
        message = "Glow color must be a valid HEX color"
    )
    private String glowColor;
}