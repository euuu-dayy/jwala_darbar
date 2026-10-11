package com.jwalanavdurga.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminFestivalDayResponse {

    private Long id;
    private Long festivalId;
    private Integer dayNumber;
    private LocalDate festivalDate;
    private String title;
    private String description;
    private Boolean visarjanDay;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AdminFestivalDayResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Long festivalId) {
        this.festivalId = festivalId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}