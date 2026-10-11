package com.jwalanavdurga.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AdminFestivalDayRequest {

    @NotNull(message = "Day number is required")
    @Min(value = 1, message = "Day number must be between 1 and 9")
    @Max(value = 9, message = "Day number must be between 1 and 9")
    private Integer dayNumber;

    @NotNull(message = "Festival date is required")
    private LocalDate festivalDate;

    @NotBlank(message = "Day title is required")
    @Size(max = 255)
    private String title;

    @Size(max = 2000)
    private String description;

    private Boolean visarjanDay;

    public AdminFestivalDayRequest() {
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

    public Boolean getVisarjanDay() {
        return visarjanDay;
    }

    public void setVisarjanDay(Boolean visarjanDay) {
        this.visarjanDay = visarjanDay;
    }
}