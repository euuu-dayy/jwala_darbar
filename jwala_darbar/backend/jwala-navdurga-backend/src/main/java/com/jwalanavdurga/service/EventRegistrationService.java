package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.EventRegistrationRequest;
import com.jwalanavdurga.dto.EventRegistrationResponse;
import com.jwalanavdurga.entity.Event;
import com.jwalanavdurga.entity.EventRegistration;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.EventRegistrationRepository;
import com.jwalanavdurga.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventRegistrationService {

    private static final Logger log =
            LoggerFactory.getLogger(EventRegistrationService.class);

    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;

    public EventRegistrationService(
            EventRepository eventRepository,
            EventRegistrationRepository registrationRepository) {

        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public EventRegistrationResponse register(
            Long eventId,
            EventRegistrationRequest request) {

        log.info(
                "Processing event registration | eventId={}",
                eventId
        );

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {

                    log.warn(
                            "Event not found for registration | eventId={}",
                            eventId
                    );

                    return new ResourceNotFoundException(
                            "Event not found."
                    );
                });

        if (!"PUBLISHED".equalsIgnoreCase(event.getStatus())) {

            log.warn(
                    "Registration attempted for unpublished event | eventId={} status={}",
                    eventId,
                    event.getStatus()
            );

            throw new BusinessException(
                    "Registration is not available for this event."
            );
        }

        if (!Boolean.TRUE.equals(event.getRegistrationRequired())) {

            log.warn(
                    "Registration attempted for event that does not require registration | eventId={}",
                    eventId
            );

            throw new BusinessException(
                    "Registration is not required for this event."
            );
        }

        boolean alreadyRegistered =
                registrationRepository
                        .existsByEventIdAndMobileNumberAndDeletedAtIsNull(
                                eventId,
                                request.getMobileNumber()
                        );

        if (alreadyRegistered) {

            log.warn(
                    "Duplicate event registration attempt | eventId={}",
                    eventId
            );

            throw new BusinessException(
                    "This mobile number is already registered for this event."
            );
        }

        if (event.getMaxParticipants() != null) {

            long currentRegistrations =
                    registrationRepository
                            .countByEventIdAndRegistrationStatusAndDeletedAtIsNull(
                                    eventId,
                                    "REGISTERED"
                            );

            if (currentRegistrations >= event.getMaxParticipants()) {

                log.warn(
                        "Event registration limit reached | eventId={} currentRegistrations={} maxParticipants={}",
                        eventId,
                        currentRegistrations,
                        event.getMaxParticipants()
                );

                throw new BusinessException(
                        "Registration limit for this event has been reached."
                );
            }
        }

        LocalDateTime now = LocalDateTime.now();

        EventRegistration registration =
                new EventRegistration();

        registration.setEventId(eventId);
        registration.setName(request.getName());
        registration.setMobileNumber(request.getMobileNumber());
        registration.setEmail(request.getEmail());
        registration.setAge(request.getAge());
        registration.setRegistrationStatus("REGISTERED");
        registration.setRegisteredAt(now);
        registration.setCreatedAt(now);
        registration.setUpdatedAt(now);

        EventRegistration saved =
                registrationRepository.save(registration);

        log.info(
                "Event registration created successfully | registrationId={} eventId={}",
                saved.getId(),
                eventId
        );

        return mapToResponse(saved);
    }

    private EventRegistrationResponse mapToResponse(
            EventRegistration registration) {

        EventRegistrationResponse response =
                new EventRegistrationResponse();

        response.setRegistrationId(
                registration.getId()
        );

        response.setEventId(
                registration.getEventId()
        );

        response.setName(
                registration.getName()
        );

        response.setMobileNumber(
                registration.getMobileNumber()
        );

        response.setEmail(
                registration.getEmail()
        );

        response.setAge(
                registration.getAge()
        );

        response.setRegistrationStatus(
                registration.getRegistrationStatus()
        );

        response.setRegisteredAt(
                registration.getRegisteredAt()
        );

        return response;
    }
}