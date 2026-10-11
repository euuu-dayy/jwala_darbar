package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.EventResponse;
import com.jwalanavdurga.dto.TodayEventsResponse;
import com.jwalanavdurga.entity.Event;
import com.jwalanavdurga.entity.Festival;
import com.jwalanavdurga.entity.FestivalDay;
import com.jwalanavdurga.repository.EventRepository;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class EventService {

    private static final Logger log =
            LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalDayRepository festivalDayRepository;

    public EventService(
            EventRepository eventRepository,
            FestivalRepository festivalRepository,
            FestivalDayRepository festivalDayRepository) {

        this.eventRepository = eventRepository;
        this.festivalRepository = festivalRepository;
        this.festivalDayRepository = festivalDayRepository;
    }

    public List<EventResponse> getEventsByFestivalDay(
            Long festivalDayId) {

        log.info(
                "Fetching published events | festivalDayId={}",
                festivalDayId
        );

        List<EventResponse> events =
                eventRepository
                        .findByFestivalDayIdAndStatusOrderByStartTimeAsc(
                                festivalDayId,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} events | festivalDayId={}",
                events.size(),
                festivalDayId
        );

        return events;
    }

    public List<EventResponse> getEventsByType(
            Long festivalDayId,
            String eventType) {

        if (eventType == null || eventType.isBlank()) {

            log.warn(
                    "Invalid event type received | festivalDayId={}",
                    festivalDayId
            );

            throw new IllegalArgumentException(
                    "Event type is required."
            );
        }

        String normalizedEventType =
                eventType.toUpperCase();

        log.info(
                "Fetching events by type | festivalDayId={} eventType={}",
                festivalDayId,
                normalizedEventType
        );

        List<EventResponse> events =
                eventRepository
                        .findByFestivalDayIdAndEventTypeAndStatusOrderByStartTimeAsc(
                                festivalDayId,
                                normalizedEventType,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} events | festivalDayId={} eventType={}",
                events.size(),
                festivalDayId,
                normalizedEventType
        );

        return events;
    }

    public TodayEventsResponse getTodayEvents() {

        LocalDate today = LocalDate.now(
                ZoneId.of("Asia/Kolkata")
        );

        log.info(
                "Fetching today's events | date={}",
                today
        );

        Festival festival = festivalRepository
                .findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        "PUBLISHED",
                        today,
                        today
                )
                .orElse(null);

        if (festival == null) {

            log.info(
                    "No published festival found for today's date | date={}",
                    today
            );

            return null;
        }

        FestivalDay festivalDay = festivalDayRepository
                .findByFestivalIdAndFestivalDate(
                        festival.getId(),
                        today
                )
                .orElse(null);

        if (festivalDay == null) {

            log.warn(
                    "Festival found but festival day is missing | festivalId={} date={}",
                    festival.getId(),
                    today
            );

            return null;
        }

        TodayEventsResponse response =
                buildTodayEventsResponse(
                        festivalDay,
                        today
                );

        log.info(
                "Today's events fetched successfully | festivalId={} dayNumber={} date={}",
                festival.getId(),
                festivalDay.getDayNumber(),
                today
        );

        return response;
    }

    public TodayEventsResponse getEventsForDate(
            LocalDate date) {

        log.info(
                "Fetching events for date | date={}",
                date
        );

        Festival festival = festivalRepository
                .findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        "PUBLISHED",
                        date,
                        date
                )
                .orElse(null);

        if (festival == null) {

            log.info(
                    "No published festival found for date | date={}",
                    date
            );

            return null;
        }

        FestivalDay festivalDay = festivalDayRepository
                .findByFestivalIdAndFestivalDate(
                        festival.getId(),
                        date
                )
                .orElse(null);

        if (festivalDay == null) {

            log.warn(
                    "Festival found but festival day is missing | festivalId={} date={}",
                    festival.getId(),
                    date
            );

            return null;
        }

        TodayEventsResponse response =
                buildTodayEventsResponse(
                        festivalDay,
                        date
                );

        log.info(
                "Events fetched successfully for date | festivalId={} dayNumber={} date={}",
                festival.getId(),
                festivalDay.getDayNumber(),
                date
        );

        return response;
    }

    private TodayEventsResponse buildTodayEventsResponse(
            FestivalDay festivalDay,
            LocalDate date) {

        List<EventResponse> dayEvents =
                getEventsByType(
                        festivalDay.getId(),
                        "DAY"
                );

        List<EventResponse> nightEvents =
                getEventsByType(
                        festivalDay.getId(),
                        "NIGHT"
                );

        List<EventResponse> specialEvents =
                getEventsByType(
                        festivalDay.getId(),
                        "SPECIAL"
                );

        TodayEventsResponse response =
                new TodayEventsResponse();

        response.setDate(date);

        response.setDayNumber(
                festivalDay.getDayNumber()
        );

        response.setDayTitle(
                festivalDay.getTitle()
        );

        response.setDayEvents(dayEvents);
        response.setNightEvents(nightEvents);
        response.setSpecialEvents(specialEvents);

        return response;
    }

    private EventResponse mapToResponse(
            Event event) {

        EventResponse response =
                new EventResponse();

        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setEventType(event.getEventType());
        response.setStartTime(event.getStartTime());
        response.setEndTime(event.getEndTime());
        response.setLocation(event.getLocation());

        response.setRegistrationRequired(
                event.getRegistrationRequired()
        );

        response.setMaxParticipants(
                event.getMaxParticipants()
        );

        response.setStatus(event.getStatus());

        return response;
    }
}