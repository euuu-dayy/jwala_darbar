package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.AnnouncementResponse;
import com.jwalanavdurga.entity.Announcement;
import com.jwalanavdurga.repository.AnnouncementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AnnouncementService {

    private static final Logger log =
            LoggerFactory.getLogger(AnnouncementService.class);

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(
            AnnouncementRepository announcementRepository) {

        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementResponse> getAllAnnouncements() {

        log.info("Fetching all published announcements");

        List<AnnouncementResponse> announcements =
                announcementRepository
                        .findByStatusAndDeletedAtIsNullOrderByPriorityAscPublishedAtDesc(
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} published announcements",
                announcements.size()
        );

        return announcements;
    }

    public List<AnnouncementResponse> getAnnouncementsByFestival(
            Long festivalId) {

        log.info(
                "Fetching announcements | festivalId={}",
                festivalId
        );

        List<AnnouncementResponse> announcements =
                announcementRepository
                        .findByFestivalIdAndStatusAndDeletedAtIsNullOrderByPriorityAscPublishedAtDesc(
                                festivalId,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} announcements | festivalId={}",
                announcements.size(),
                festivalId
        );

        return announcements;
    }

    public List<AnnouncementResponse> getActiveAnnouncements() {

        LocalDateTime now = LocalDateTime.now(
                ZoneId.of("Asia/Kolkata")
        );

        log.info(
                "Fetching active announcements | time={}",
                now
        );

        List<AnnouncementResponse> announcements =
                announcementRepository
                        .findActiveAnnouncements(
                                "PUBLISHED",
                                now
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} active announcements",
                announcements.size()
        );

        return announcements;
    }

    private AnnouncementResponse mapToResponse(
            Announcement announcement) {

        AnnouncementResponse response =
                new AnnouncementResponse();

        response.setId(announcement.getId());
        response.setFestivalId(
                announcement.getFestivalId()
        );
        response.setFestivalDayId(
                announcement.getFestivalDayId()
        );
        response.setTitle(
                announcement.getTitle()
        );
        response.setContent(
                announcement.getContent()
        );
        response.setPriority(
                announcement.getPriority()
        );
        response.setStatus(
                announcement.getStatus()
        );
        response.setPublishedAt(
                announcement.getPublishedAt()
        );
        response.setExpiresAt(
                announcement.getExpiresAt()
        );

        return response;
    }
}