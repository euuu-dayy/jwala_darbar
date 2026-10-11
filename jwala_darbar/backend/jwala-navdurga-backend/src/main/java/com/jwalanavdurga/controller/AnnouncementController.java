package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.AnnouncementResponse;
import com.jwalanavdurga.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(
            AnnouncementService announcementService) {

        this.announcementService = announcementService;
    }

    @GetMapping
    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/festival/{festivalId}")
    public List<AnnouncementResponse> getAnnouncementsByFestival(
            @PathVariable Long festivalId) {

        return announcementService
                .getAnnouncementsByFestival(festivalId);
    }

    @GetMapping("/active")
    public List<AnnouncementResponse> getActiveAnnouncements() {

        return announcementService.getActiveAnnouncements();
    }
}