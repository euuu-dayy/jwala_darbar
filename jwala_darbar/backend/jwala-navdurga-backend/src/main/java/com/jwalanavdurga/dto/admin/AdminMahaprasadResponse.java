package com.jwalanavdurga.dto.admin;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class AdminMahaprasadResponse {

    private Long id;

    private Long festivalId;

    private Long festivalDayId;

    private String title;

    private String description;

    private LocalDate eventDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private Integer estimatedPeople;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}