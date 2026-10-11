package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.ContactMessageRequest;
import com.jwalanavdurga.dto.ContactMessageResponse;
import com.jwalanavdurga.entity.ContactMessage;
import com.jwalanavdurga.repository.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactMessageService {

    private static final Logger log =
            LoggerFactory.getLogger(ContactMessageService.class);

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageService(
            ContactMessageRepository contactMessageRepository) {

        this.contactMessageRepository = contactMessageRepository;
    }

    public ContactMessageResponse createMessage(
            ContactMessageRequest request) {

        log.info(
                "Creating new contact message | subject={}",
                request.getSubject()
        );

        ContactMessage message =
                new ContactMessage();

        message.setName(request.getName());
        message.setMobileNumber(request.getMobileNumber());
        message.setEmail(request.getEmail());
        message.setSubject(request.getSubject());
        message.setMessage(request.getMessage());

        message.setStatus("NEW");

        LocalDateTime now =
                LocalDateTime.now();

        message.setCreatedAt(now);
        message.setUpdatedAt(now);

        ContactMessage saved =
                contactMessageRepository.save(message);

        log.info(
                "Contact message created successfully | messageId={}",
                saved.getId()
        );

        return mapToResponse(saved);
    }

    public List<ContactMessageResponse> getByStatus(
            String status) {

        log.info(
                "Fetching contact messages | status={}",
                status
        );

        List<ContactMessageResponse> messages =
                contactMessageRepository
                        .findByStatusOrderByCreatedAtDesc(status)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} contact messages | status={}",
                messages.size(),
                status
        );

        return messages;
    }

    private ContactMessageResponse mapToResponse(
            ContactMessage message) {

        ContactMessageResponse response =
                new ContactMessageResponse();

        response.setId(message.getId());
        response.setName(message.getName());
        response.setMobileNumber(message.getMobileNumber());
        response.setEmail(message.getEmail());
        response.setSubject(message.getSubject());
        response.setMessage(message.getMessage());
        response.setStatus(message.getStatus());
        response.setResolvedAt(message.getResolvedAt());
        response.setResolvedBy(message.getResolvedBy());
        response.setCreatedAt(message.getCreatedAt());
        response.setUpdatedAt(message.getUpdatedAt());

        return response;
    }
}