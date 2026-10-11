package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.FestivalResponse;
import com.jwalanavdurga.dto.TodayFestivalResponse;
import com.jwalanavdurga.service.FestivalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @GetMapping
    public List<FestivalResponse> getAllFestivals() {
        return festivalService.getAllFestivals();
    }
    
    @GetMapping("/current/today")
    public TodayFestivalResponse getTodayFestival() {
        return festivalService.getTodayFestival();
    }
    
//    @GetMapping("/1/today-test")
//    public TodayFestivalResponse getFestivalForTest() {
//        return festivalService.getFestivalForDate(
//                1L,
//                LocalDate.of(2026, 9, 18)
//        );
//    }
    
    @GetMapping("/current/date/{date}")
    public TodayFestivalResponse getFestivalForDate(
            @PathVariable LocalDate date) {

        return festivalService.getFestivalForDate(date);
    }
}