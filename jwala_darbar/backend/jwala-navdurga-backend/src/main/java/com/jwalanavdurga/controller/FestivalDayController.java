package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.FestivalDayResponse;
import com.jwalanavdurga.service.FestivalDayService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/festivals")
public class FestivalDayController {

    private final FestivalDayService festivalDayService;

    public FestivalDayController(
            FestivalDayService festivalDayService) {

        this.festivalDayService = festivalDayService;
    }

    @GetMapping("/{festivalId}/days")
    public List<FestivalDayResponse> getFestivalDays(
            @PathVariable Long festivalId) {

        return festivalDayService.getDaysByFestival(festivalId);
    }

    @GetMapping("/{festivalId}/days/date/{date}")
    public FestivalDayResponse getFestivalDayByDate(
            @PathVariable Long festivalId,
            @PathVariable LocalDate date) {

        return festivalDayService.getDayByDate(
                festivalId,
                date
        );
    }
} 	