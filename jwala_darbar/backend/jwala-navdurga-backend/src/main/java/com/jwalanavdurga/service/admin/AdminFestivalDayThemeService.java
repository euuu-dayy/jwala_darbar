package com.jwalanavdurga.service.admin;

import com.jwalanavdurga.dto.admin.AdminFestivalDayThemeRequest;
import com.jwalanavdurga.dto.admin.AdminFestivalDayThemeResponse;
import com.jwalanavdurga.entity.FestivalDay;
import com.jwalanavdurga.entity.FestivalDayTheme;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalDayThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminFestivalDayThemeService {

    private final FestivalDayThemeRepository themeRepository;
    private final FestivalDayRepository festivalDayRepository;

    @Transactional
    public AdminFestivalDayThemeResponse create(
            Long festivalDayId,
            AdminFestivalDayThemeRequest request) {

        FestivalDay festivalDay = festivalDayRepository.findById(festivalDayId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival day not found: " + festivalDayId));

        if (themeRepository.existsByFestivalDayId(festivalDayId)) {
        	throw new IllegalStateException(
        		    "Theme already exists for festival day: " + festivalDayId
        		);
        }

        FestivalDayTheme theme = new FestivalDayTheme();

        theme.setFestivalDayId(festivalDay.getId());
        theme.setPrimaryColor(request.getPrimaryColor());
        theme.setSecondaryColor(request.getSecondaryColor());
        theme.setAccentColor(request.getAccentColor());
        theme.setAccentLightColor(request.getAccentLightColor());
        theme.setAccentDarkColor(request.getAccentDarkColor());
        theme.setGlowColor(request.getGlowColor());

        FestivalDayTheme saved = themeRepository.save(theme);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public AdminFestivalDayThemeResponse getByFestivalDayId(
            Long festivalDayId) {

        if (!festivalDayRepository.existsById(festivalDayId)) {
            throw new ResourceNotFoundException(
                    "Festival day not found: " + festivalDayId);
        }

        FestivalDayTheme theme = themeRepository
                .findByFestivalDayId(festivalDayId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theme not found for festival day: "
                                        + festivalDayId));

        return mapToResponse(theme);
    }

    @Transactional
    public AdminFestivalDayThemeResponse update(
            Long festivalDayId,
            AdminFestivalDayThemeRequest request) {

        FestivalDayTheme theme = themeRepository
                .findByFestivalDayId(festivalDayId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theme not found for festival day: "
                                        + festivalDayId));

        theme.setPrimaryColor(request.getPrimaryColor());
        theme.setSecondaryColor(request.getSecondaryColor());
        theme.setAccentColor(request.getAccentColor());
        theme.setAccentLightColor(request.getAccentLightColor());
        theme.setAccentDarkColor(request.getAccentDarkColor());
        theme.setGlowColor(request.getGlowColor());

        FestivalDayTheme updated = themeRepository.save(theme);

        return mapToResponse(updated);
    }

    @Transactional
    public void delete(Long festivalDayId) {

        FestivalDayTheme theme = themeRepository
                .findByFestivalDayId(festivalDayId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theme not found for festival day: "
                                        + festivalDayId));

        themeRepository.delete(theme);
    }

    private AdminFestivalDayThemeResponse mapToResponse(
            FestivalDayTheme theme) {

        return AdminFestivalDayThemeResponse.builder()
                .id(theme.getId())
                .festivalDayId(theme.getFestivalDayId())
                .primaryColor(theme.getPrimaryColor())
                .secondaryColor(theme.getSecondaryColor())
                .accentColor(theme.getAccentColor())
                .accentLightColor(theme.getAccentLightColor())
                .accentDarkColor(theme.getAccentDarkColor())
                .glowColor(theme.getGlowColor())
                .build();
    }
}