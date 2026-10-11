package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.MahaprasadResponse;
import com.jwalanavdurga.entity.MahaprasadEvent;
import com.jwalanavdurga.repository.MahaprasadEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class MahaprasadEventService {

    private static final Logger log =
            LoggerFactory.getLogger(MahaprasadEventService.class);

    private final MahaprasadEventRepository mahaprasadEventRepository;

    public MahaprasadEventService(
            MahaprasadEventRepository mahaprasadEventRepository) {

        this.mahaprasadEventRepository = mahaprasadEventRepository;
    }

    public List<MahaprasadResponse> getAllMahaprasadEvents() {

        log.info("Fetching all published Mahaprasad events");

        List<MahaprasadResponse> events =
                mahaprasadEventRepository
                        .findByStatusAndDeletedAtIsNullOrderByEventDateAscStartTimeAsc(
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} published Mahaprasad events",
                events.size()
        );

        return events;
    }

    public List<MahaprasadResponse> getByFestival(Long festivalId) {

        log.info(
                "Fetching Mahaprasad events | festivalId={}",
                festivalId
        );

        List<MahaprasadResponse> events =
                mahaprasadEventRepository
                        .findByFestivalIdAndStatusAndDeletedAtIsNullOrderByEventDateAscStartTimeAsc(
                                festivalId,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} Mahaprasad events | festivalId={}",
                events.size(),
                festivalId
        );

        return events;
    }

    public List<MahaprasadResponse> getByFestivalDay(
            Long festivalDayId) {

        log.info(
                "Fetching Mahaprasad events | festivalDayId={}",
                festivalDayId
        );

        List<MahaprasadResponse> events =
                mahaprasadEventRepository
                        .findByFestivalDayIdAndStatusAndDeletedAtIsNullOrderByStartTimeAsc(
                                festivalDayId,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} Mahaprasad events | festivalDayId={}",
                events.size(),
                festivalDayId
        );

        return events;
    }

    public List<MahaprasadResponse> getTodayMahaprasad() {

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        log.info(
                "Fetching today's Mahaprasad events | date={}",
                today
        );

        return getByDate(today);
    }

    public List<MahaprasadResponse> getByDate(LocalDate date) {

        log.info(
                "Fetching Mahaprasad events | date={}",
                date
        );

        List<MahaprasadResponse> events =
                mahaprasadEventRepository
                        .findByEventDateAndStatusAndDeletedAtIsNullOrderByStartTimeAsc(
                                date,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} Mahaprasad events | date={}",
                events.size(),
                date
        );

        return events;
    }

    private MahaprasadResponse mapToResponse(
            MahaprasadEvent event) {

        MahaprasadResponse response =
                new MahaprasadResponse();

        response.setId(event.getId());
        response.setFestivalId(event.getFestivalId());
        response.setFestivalDayId(event.getFestivalDayId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setEventDate(event.getEventDate());
        response.setStartTime(event.getStartTime());
        response.setEndTime(event.getEndTime());
        response.setLocation(event.getLocation());
        response.setEstimatedPeople(event.getEstimatedPeople());
        response.setStatus(event.getStatus());

        return response;
    }
}