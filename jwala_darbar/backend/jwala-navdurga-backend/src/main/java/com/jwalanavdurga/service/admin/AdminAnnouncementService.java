package com.jwalanavdurga.service.admin;

import com.jwalanavdurga.dto.admin.AdminAnnouncementRequest;
import com.jwalanavdurga.dto.admin.AdminAnnouncementResponse;
import com.jwalanavdurga.entity.Announcement;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.AnnouncementRepository;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalDayRepository festivalDayRepository;

    @Transactional(readOnly = true)
    public List<AdminAnnouncementResponse> getAll() {

        return announcementRepository
                .findByDeletedAtIsNullOrderByPublishedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminAnnouncementResponse getById(Long id) {

        Announcement announcement = announcementRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Announcement not found: " + id
                        ));

        return mapToResponse(announcement);
    }

    @Transactional
    public AdminAnnouncementResponse create(
            AdminAnnouncementRequest request) {

        validateReferences(
                request.getFestivalId(),
                request.getFestivalDayId()
        );

        validateDates(request);

        String title = request.getTitle().trim();

        if (announcementRepository
                .existsByTitleAndDeletedAtIsNull(title)) {

            throw new BusinessException(
                    "Announcement with this title already exists"
            );
        }

        Announcement announcement = new Announcement();

        announcement.setFestivalId(request.getFestivalId());
        announcement.setFestivalDayId(request.getFestivalDayId());
        announcement.setTitle(title);
        announcement.setContent(request.getContent().trim());

        announcement.setPriority(
                request.getPriority() != null
                        ? request.getPriority()
                        : 0
        );

        announcement.setStatus("DRAFT");
        announcement.setExpiresAt(request.getExpiresAt());

        LocalDateTime now = LocalDateTime.now();

        announcement.setCreatedAt(now);
        announcement.setUpdatedAt(now);

        Announcement saved =
                announcementRepository.save(announcement);

        return mapToResponse(saved);
    }

    @Transactional
    public AdminAnnouncementResponse update(
            Long id,
            AdminAnnouncementRequest request) {

        Announcement announcement = announcementRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Announcement not found: " + id
                        ));

        validateReferences(
                request.getFestivalId(),
                request.getFestivalDayId()
        );

        validateDates(request);

        String title = request.getTitle().trim();

        if (announcementRepository
                .existsByTitleAndIdNotAndDeletedAtIsNull(
                        title,
                        id
                )) {

            throw new BusinessException(
                    "Announcement with this title already exists"
            );
        }

        announcement.setFestivalId(request.getFestivalId());
        announcement.setFestivalDayId(request.getFestivalDayId());
        announcement.setTitle(title);
        announcement.setContent(request.getContent().trim());

        announcement.setPriority(
                request.getPriority() != null
                        ? request.getPriority()
                        : 0
        );

        announcement.setExpiresAt(request.getExpiresAt());
        announcement.setUpdatedAt(LocalDateTime.now());

        Announcement updated =
                announcementRepository.save(announcement);

        return mapToResponse(updated);
    }

    @Transactional
    public AdminAnnouncementResponse publish(Long id) {

        Announcement announcement = announcementRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Announcement not found: " + id
                        ));

        if ("PUBLISHED".equals(announcement.getStatus())) {
            throw new BusinessException(
                    "Announcement is already published"
            );
        }

        if (announcement.getExpiresAt() != null
                && !announcement.getExpiresAt().isAfter(
                        LocalDateTime.now())) {

            throw new BusinessException(
                    "Cannot publish an expired announcement"
            );
        }

        announcement.setStatus("PUBLISHED");
        announcement.setPublishedAt(LocalDateTime.now());
        announcement.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                announcementRepository.save(announcement)
        );
    }

    @Transactional
    public AdminAnnouncementResponse unpublish(Long id) {

        Announcement announcement = announcementRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Announcement not found: " + id
                        ));

        announcement.setStatus("DRAFT");
        announcement.setPublishedAt(null);
        announcement.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                announcementRepository.save(announcement)
        );
    }

    @Transactional
    public void delete(Long id) {

        Announcement announcement = announcementRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Announcement not found: " + id
                        ));

        LocalDateTime now = LocalDateTime.now();

        announcement.setStatus("DELETED");
        announcement.setDeletedAt(now);
        announcement.setUpdatedAt(now);

        announcementRepository.save(announcement);
    }

    private void validateReferences(
            Long festivalId,
            Long festivalDayId) {

        if (festivalId != null
                && !festivalRepository.existsById(festivalId)) {

            throw new ResourceNotFoundException(
                    "Festival not found: " + festivalId
            );
        }

        if (festivalDayId != null
                && !festivalDayRepository.existsById(festivalDayId)) {

            throw new ResourceNotFoundException(
                    "Festival day not found: " + festivalDayId
            );
        }
    }

    private void validateDates(
            AdminAnnouncementRequest request) {

        if (request.getExpiresAt() != null
                && !request.getExpiresAt().isAfter(
                        LocalDateTime.now())) {

            throw new BusinessException(
                    "Expiry date must be in the future"
            );
        }
    }

    private AdminAnnouncementResponse mapToResponse(
            Announcement announcement) {

        return AdminAnnouncementResponse.builder()
                .id(announcement.getId())
                .festivalId(announcement.getFestivalId())
                .festivalDayId(announcement.getFestivalDayId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .priority(announcement.getPriority())
                .status(announcement.getStatus())
                .publishedAt(announcement.getPublishedAt())
                .expiresAt(announcement.getExpiresAt())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();
    }
}