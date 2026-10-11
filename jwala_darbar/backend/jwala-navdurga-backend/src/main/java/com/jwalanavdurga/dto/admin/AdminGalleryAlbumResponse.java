package com.jwalanavdurga.dto.admin;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminGalleryAlbumResponse {

    private Long id;

    private Long festivalId;

    private Long festivalDayId;

    private String title;

    private String description;

    private String driveUrl;

    private Integer displayOrder;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}