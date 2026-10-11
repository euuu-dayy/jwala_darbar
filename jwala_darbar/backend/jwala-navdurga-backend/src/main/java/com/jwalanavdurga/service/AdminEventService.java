package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.AdminEventRequest;
import com.jwalanavdurga.dto.AdminEventResponse;
import com.jwalanavdurga.entity.Event;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.EventRepository;
import com.jwalanavdurga.repository.FestivalDayRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminEventService {

    private final EventRepository eventRepository;
    private final FestivalDayRepository festivalDayRepository;

    public AdminEventService(
            EventRepository eventRepository,
            FestivalDayRepository festivalDayRepository) {

        this.eventRepository = eventRepository;
        this.festivalDayRepository = festivalDayRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminEventResponse> getAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminEventResponse getEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        return mapToResponse(event);
    }

    @Transactional
    public AdminEventResponse createEvent(
            AdminEventRequest request) {

        validateEventRequest(request);

        if (!festivalDayRepository.existsById(
                request.getFestivalDayId())) {

            throw new ResourceNotFoundException(
                    "Festival day not found with id: "
                            + request.getFestivalDayId()
            );
        }

        Event event = new Event();

        event.setFestivalDayId(request.getFestivalDayId());
        event.setCategoryId(request.getCategoryId());
        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription());
        event.setEventType(
                request.getEventType().trim().toUpperCase()
        );
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setLocation(request.getLocation());
        event.setRegistrationRequired(
                Boolean.TRUE.equals(
                        request.getRegistrationRequired()
                )
        );
        event.setMaxParticipants(
                request.getMaxParticipants()
        );

        // New events are not published automatically.
        event.setStatus("DRAFT");

        LocalDateTime now = LocalDateTime.now();

        event.setCreatedAt(now);
        event.setUpdatedAt(now);

        Event savedEvent = eventRepository.save(event);

        return mapToResponse(savedEvent);
    }

    @Transactional
    public AdminEventResponse updateEvent(
            Long id,
            AdminEventRequest request) {

        validateEventRequest(request);

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        if (!festivalDayRepository.existsById(
                request.getFestivalDayId())) {

            throw new ResourceNotFoundException(
                    "Festival day not found with id: "
                            + request.getFestivalDayId()
            );
        }

        event.setFestivalDayId(request.getFestivalDayId());
        event.setCategoryId(request.getCategoryId());
        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription());
        event.setEventType(
                request.getEventType().trim().toUpperCase()
        );
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setLocation(request.getLocation());
        event.setRegistrationRequired(
                Boolean.TRUE.equals(
                        request.getRegistrationRequired()
                )
        );
        event.setMaxParticipants(
                request.getMaxParticipants()
        );
        event.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                eventRepository.save(event)
        );
    }

    @Transactional
    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        event.setStatus("DELETED");
        event.setUpdatedAt(LocalDateTime.now());

        eventRepository.save(event);
    }

    @Transactional
    public AdminEventResponse publishEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        if ("DELETED".equalsIgnoreCase(event.getStatus())) {
            throw new BusinessException(
                    "Deleted event cannot be published"
            );
        }

        event.setStatus("PUBLISHED");
        event.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                eventRepository.save(event)
        );
    }

    private void validateEventRequest(
            AdminEventRequest request) {

        String eventType =
                request.getEventType()
                        .trim()
                        .toUpperCase();

        if (!eventType.equals("DAY")
                && !eventType.equals("NIGHT")
                && !eventType.equals("SPECIAL")) {

            throw new BusinessException(
                    "Event type must be DAY, NIGHT or SPECIAL"
            );
        }

        if (request.getStartTime() != null
                && request.getEndTime() != null
                && !request.getEndTime()
                        .isAfter(request.getStartTime())) {

            throw new BusinessException(
                    "End time must be after start time"
            );
        }

        if (Boolean.TRUE.equals(
                request.getRegistrationRequired())
                && request.getMaxParticipants() == null) {

            throw new BusinessException(
                    "Maximum participants is required when registration is enabled"
            );
        }

        if (!Boolean.TRUE.equals(
                request.getRegistrationRequired())
                && request.getMaxParticipants() != null) {

            throw new BusinessException(
                    "Maximum participants should only be provided for registered events"
            );
        }
    }

    private AdminEventResponse mapToResponse(
            Event event) {

        AdminEventResponse response =
                new AdminEventResponse();

        response.setId(event.getId());
        response.setFestivalDayId(event.getFestivalDayId());
        response.setCategoryId(event.getCategoryId());
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
        response.setCreatedAt(event.getCreatedAt());
        response.setUpdatedAt(event.getUpdatedAt());

        return response;
    }
}