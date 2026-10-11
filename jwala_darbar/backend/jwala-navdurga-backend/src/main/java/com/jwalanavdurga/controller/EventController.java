package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.EventResponse;
import com.jwalanavdurga.dto.TodayEventsResponse;
import com.jwalanavdurga.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/public/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/day/{festivalDayId}")
    public List<EventResponse> getEventsByDay(
            @PathVariable Long festivalDayId) {

        return eventService.getEventsByFestivalDay(
                festivalDayId
        );
    }

    @GetMapping("/day/{festivalDayId}/type/{eventType}")
    public List<EventResponse> getEventsByType(
            @PathVariable Long festivalDayId,
            @PathVariable String eventType) {

        return eventService.getEventsByType(
                festivalDayId,
                eventType
        );
    }
    
    @GetMapping("/today")
    public TodayEventsResponse getTodayEvents() {
        return eventService.getTodayEvents();
    }
    
    @GetMapping("/date/{date}")
    public TodayEventsResponse getEventsForDate(
            @PathVariable LocalDate date) {

        return eventService.getEventsForDate(date);
    }
}