package com.jwalanavdurga.dto;

import jakarta.validation.constraints.*;

import java.time.LocalTime;

public class AdminEventRequest {

    @NotNull(message = "Festival day ID is required")
    private Long festivalDayId;

    @NotNull(message = "Event category ID is required")
    private Long categoryId;

    @NotBlank(message = "Event title is required")
    @Size(max = 255, message = "Event title cannot exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotBlank(message = "Event type is required")
    private String eventType;

    private LocalTime startTime;

    private LocalTime endTime;

    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    private Boolean registrationRequired;

    @Min(value = 1, message = "Maximum participants must be greater than 0")
    private Integer maxParticipants;

    public AdminEventRequest() {
    }

    public Long getFestivalDayId() {
        return festivalDayId;
    }

    public void setFestivalDayId(Long festivalDayId) {
        this.festivalDayId = festivalDayId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(Boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}