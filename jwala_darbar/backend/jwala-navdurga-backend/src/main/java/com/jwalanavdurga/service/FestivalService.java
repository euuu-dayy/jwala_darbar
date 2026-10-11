package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.FestivalDayResponse;
import com.jwalanavdurga.dto.FestivalResponse;
import com.jwalanavdurga.dto.TodayFestivalResponse;
import com.jwalanavdurga.entity.Festival;
import com.jwalanavdurga.entity.FestivalDay;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class FestivalService {

    private static final Logger log =
            LoggerFactory.getLogger(FestivalService.class);

    private final FestivalRepository festivalRepository;
    private final FestivalDayRepository festivalDayRepository;
    private final FestivalDayService festivalDayService;

    public FestivalService(
            FestivalRepository festivalRepository,
            FestivalDayRepository festivalDayRepository,
            FestivalDayService festivalDayService) {

        this.festivalRepository = festivalRepository;
        this.festivalDayRepository = festivalDayRepository;
        this.festivalDayService = festivalDayService;
    }

    public List<FestivalResponse> getAllFestivals() {

        log.info("Fetching all festivals");

        List<FestivalResponse> festivals =
                festivalRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} festivals",
                festivals.size()
        );

        return festivals;
    }

    public TodayFestivalResponse getTodayFestival() {

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        log.info(
                "Fetching festival for today | date={}",
                today
        );

        Festival festival = festivalRepository
                .findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        "PUBLISHED",
                        today,
                        today
                )
                .orElse(null);

        if (festival == null) {

            log.info(
                    "No published festival found for today | date={}",
                    today
            );

            return null;
        }

        FestivalDay day = festivalDayRepository
                .findByFestivalIdAndFestivalDate(
                        festival.getId(),
                        today
                )
                .orElse(null);

        if (day == null) {

            log.warn(
                    "Festival found but festival day is missing | festivalId={} date={}",
                    festival.getId(),
                    today
            );

            return null;
        }

        FestivalDayResponse dayResponse =
                festivalDayService.mapToResponse(day);

        TodayFestivalResponse response =
                new TodayFestivalResponse();

        response.setFestival(mapToResponse(festival));
        response.setDay(dayResponse);

        log.info(
                "Today's festival fetched successfully | festivalId={} dayNumber={}",
                festival.getId(),
                day.getDayNumber()
        );

        return response;
    }

    public TodayFestivalResponse getFestivalForDate(
            LocalDate date) {

        log.info(
                "Fetching festival for date | date={}",
                date
        );

        Festival festival = festivalRepository
                .findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        "PUBLISHED",
                        date,
                        date
                )
                .orElse(null);

        if (festival == null) {

            log.info(
                    "No published festival found for date | date={}",
                    date
            );

            return null;
        }

        FestivalDay day = festivalDayRepository
                .findByFestivalIdAndFestivalDate(
                        festival.getId(),
                        date
                )
                .orElse(null);

        if (day == null) {

            log.warn(
                    "Festival found but festival day is missing | festivalId={} date={}",
                    festival.getId(),
                    date
            );

            return null;
        }

        TodayFestivalResponse response =
                new TodayFestivalResponse();

        response.setFestival(mapToResponse(festival));

        response.setDay(
                festivalDayService.mapToResponse(day)
        );

        log.info(
                "Festival fetched successfully for date | festivalId={} dayNumber={} date={}",
                festival.getId(),
                day.getDayNumber(),
                date
        );

        return response;
    }

    private FestivalResponse mapToResponse(
            Festival festival) {

        FestivalResponse response =
                new FestivalResponse();

        response.setId(festival.getId());
        response.setName(festival.getName());
        response.setYear(festival.getYear());
        response.setStartDate(festival.getStartDate());
        response.setEndDate(festival.getEndDate());
        response.setDescription(festival.getDescription());
        response.setStatus(festival.getStatus());

        return response;
    }
}