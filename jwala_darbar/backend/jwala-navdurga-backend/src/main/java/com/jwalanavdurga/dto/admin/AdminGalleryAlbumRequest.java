package com.jwalanavdurga.dto.admin;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminGalleryAlbumRequest {

    private Long festivalId;

    private Long festivalDayId;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Google Drive URL is required")
    @Size(max = 1000, message = "Google Drive URL must not exceed 1000 characters")
    private String driveUrl;

    @Min(value = 0, message = "Display order cannot be negative")
    private Integer displayOrder;
}