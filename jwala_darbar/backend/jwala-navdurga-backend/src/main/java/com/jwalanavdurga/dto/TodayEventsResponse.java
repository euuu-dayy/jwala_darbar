package com.jwalanavdurga.dto;

import java.time.LocalDate;
import java.util.List;

public class TodayEventsResponse {

    private LocalDate date;
    private Integer dayNumber;
    private String dayTitle;

    private List<EventResponse> dayEvents;
    private List<EventResponse> nightEvents;
    private List<EventResponse> specialEvents;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public List<EventResponse> getDayEvents() {
        return dayEvents;
    }

    public void setDayEvents(List<EventResponse> dayEvents) {
        this.dayEvents = dayEvents;
    }

    public List<EventResponse> getNightEvents() {
        return nightEvents;
    }

    public void setNightEvents(List<EventResponse> nightEvents) {
        this.nightEvents = nightEvents;
    }

    public List<EventResponse> getSpecialEvents() {
        return specialEvents;
    }

    public void setSpecialEvents(List<EventResponse> specialEvents) {
        this.specialEvents = specialEvents;
    }
}