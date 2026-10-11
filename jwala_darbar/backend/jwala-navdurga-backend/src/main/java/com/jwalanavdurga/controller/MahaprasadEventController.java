package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.MahaprasadResponse;
import com.jwalanavdurga.service.MahaprasadEventService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/mahaprasad")
public class MahaprasadEventController {

    private final MahaprasadEventService mahaprasadEventService;

    public MahaprasadEventController(
            MahaprasadEventService mahaprasadEventService) {

        this.mahaprasadEventService = mahaprasadEventService;
    }

    @GetMapping
    public List<MahaprasadResponse> getAllMahaprasadEvents() {

        return mahaprasadEventService.getAllMahaprasadEvents();
    }

    @GetMapping("/festival/{festivalId}")
    public List<MahaprasadResponse> getByFestival(
            @PathVariable Long festivalId) {

        return mahaprasadEventService.getByFestival(festivalId);
    }

    @GetMapping("/day/{festivalDayId}")
    public List<MahaprasadResponse> getByFestivalDay(
            @PathVariable Long festivalDayId) {

        return mahaprasadEventService.getByFestivalDay(festivalDayId);
    }

    @GetMapping("/today")
    public List<MahaprasadResponse> getTodayMahaprasad() {

        return mahaprasadEventService.getTodayMahaprasad();
    }

    @GetMapping("/date/{date}")
    public List<MahaprasadResponse> getByDate(
            @PathVariable LocalDate date) {

        return mahaprasadEventService.getByDate(date);
    }
}