package com.jwalanavdurga.dto.admin;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AdminMahaprasadRequest {

    @NotNull(message = "Festival ID is required")
    private Long festivalId;

    private Long festivalDayId;

    @NotBlank(message = "Title is required")
    @Size(
            min = 3,
            max = 255,
            message = "Title must be between 3 and 255 characters"
    )
    private String title;

    @Size(
            max = 5000,
            message = "Description cannot exceed 5000 characters"
    )
    private String description;

    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    private LocalTime endTime;

    @NotBlank(message = "Location is required")
    @Size(
            max = 255,
            message = "Location cannot exceed 255 characters"
    )
    private String location;

    @Min(
            value = 1,
            message = "Estimated people must be greater than 0"
    )
    private Integer estimatedPeople;
}