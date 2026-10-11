package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.FestivalDayResponse;
import com.jwalanavdurga.dto.FestivalDayThemeResponse;
import com.jwalanavdurga.entity.FestivalDay;
import com.jwalanavdurga.entity.FestivalDayTheme;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalDayThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FestivalDayService {

    private static final Logger log =
            LoggerFactory.getLogger(FestivalDayService.class);

    private final FestivalDayRepository festivalDayRepository;
    private final FestivalDayThemeRepository themeRepository;

    public FestivalDayService(
            FestivalDayRepository festivalDayRepository,
            FestivalDayThemeRepository themeRepository) {

        this.festivalDayRepository = festivalDayRepository;
        this.themeRepository = themeRepository;
    }

    public List<FestivalDayResponse> getDaysByFestival(
            Long festivalId) {

        log.info(
                "Fetching festival days | festivalId={}",
                festivalId
        );

        List<FestivalDayResponse> days =
                festivalDayRepository
                        .findByFestivalIdOrderByDayNumberAsc(festivalId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} festival days | festivalId={}",
                days.size(),
                festivalId
        );

        return days;
    }

    public FestivalDayResponse getDayByDate(
            Long festivalId,
            LocalDate date) {

        log.info(
                "Fetching festival day | festivalId={} date={}",
                festivalId,
                date
        );

        FestivalDay day = festivalDayRepository
                .findByFestivalIdAndFestivalDate(
                        festivalId,
                        date
                )
                .orElseThrow(() -> {

                    log.warn(
                            "Festival day not found | festivalId={} date={}",
                            festivalId,
                            date
                    );

                    return new ResourceNotFoundException(
                            "Festival day not found for the specified date."
                    );
                });

        log.info(
                "Festival day fetched successfully | festivalId={} dayNumber={} date={}",
                festivalId,
                day.getDayNumber(),
                date
        );

        return mapToResponse(day);
    }

    public FestivalDayResponse mapToResponse(
            FestivalDay day) {

        FestivalDayResponse response =
                new FestivalDayResponse();

        response.setId(day.getId());
        response.setDayNumber(day.getDayNumber());
        response.setFestivalDate(day.getFestivalDate());
        response.setTitle(day.getTitle());
        response.setDescription(day.getDescription());
        response.setIsVisarjanDay(day.getIsVisarjanDay());

        FestivalDayTheme theme =
                themeRepository
                        .findByFestivalDayId(day.getId())
                        .orElse(null);

        if (theme != null) {

            FestivalDayThemeResponse themeResponse =
                    new FestivalDayThemeResponse();

            themeResponse.setPrimaryColor(
                    theme.getPrimaryColor()
            );

            themeResponse.setSecondaryColor(
                    theme.getSecondaryColor()
            );

            themeResponse.setAccentColor(
                    theme.getAccentColor()
            );

            themeResponse.setAccentLightColor(
                    theme.getAccentLightColor()
            );

            themeResponse.setAccentDarkColor(
                    theme.getAccentDarkColor()
            );

            themeResponse.setGlowColor(
                    theme.getGlowColor()
            );

            response.setTheme(themeResponse);

        } else {

            log.warn(
                    "Festival day theme not found | festivalDayId={}",
                    day.getId()
            );
        }

        return response;
    }
}