package com.jwalanavdurga.dto;

import java.time.LocalDate;

public class FestivalDayResponse {

    private Long id;
    private Integer dayNumber;
    private LocalDate festivalDate;
    private String title;
    private String description;
    private Boolean isVisarjanDay;
    private FestivalDayThemeResponse theme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public LocalDate getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(LocalDate festivalDate) {
        this.festivalDate = festivalDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsVisarjanDay() {
        return isVisarjanDay;
    }

    public void setIsVisarjanDay(Boolean visarjanDay) {
        isVisarjanDay = visarjanDay;
    }

    public FestivalDayThemeResponse getTheme() {
        return theme;
    }

    public void setTheme(FestivalDayThemeResponse theme) {
        this.theme = theme;
    }
}