package com.jwalanavdurga.dto.admin;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminAnnouncementRequest {

    private Long festivalId;

    private Long festivalDayId;

    @Size(
        min = 3,
        max = 200,
        message = "Title must be between 3 and 200 characters"
    )
    private String title;

    @Size(
        min = 1,
        max = 5000,
        message = "Content must be between 1 and 5000 characters"
    )
    private String content;

    private Integer priority;

    private LocalDateTime expiresAt;
}