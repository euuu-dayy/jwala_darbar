package com.jwalanavdurga.dto.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminFestivalDayThemeResponse {

    private Long id;

    private Long festivalDayId;

    private String primaryColor;

    private String secondaryColor;

    private String accentColor;

    private String accentLightColor;

    private String accentDarkColor;

    private String glowColor;
}