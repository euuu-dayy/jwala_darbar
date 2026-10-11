package com.jwalanavdurga.dto.admin;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminAnnouncementResponse {

    private Long id;

    private Long festivalId;

    private Long festivalDayId;

    private String title;

    private String content;

    private Integer priority;

    private String status;

    private LocalDateTime publishedAt;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}