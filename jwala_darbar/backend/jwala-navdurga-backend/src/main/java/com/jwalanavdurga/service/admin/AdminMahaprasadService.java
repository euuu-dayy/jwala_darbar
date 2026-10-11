package com.jwalanavdurga.service.admin;

import com.jwalanavdurga.dto.admin.AdminMahaprasadRequest;
import com.jwalanavdurga.dto.admin.AdminMahaprasadResponse;
import com.jwalanavdurga.entity.FestivalDay;
import com.jwalanavdurga.entity.MahaprasadEvent;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalRepository;
import com.jwalanavdurga.repository.MahaprasadEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMahaprasadService {

    private final MahaprasadEventRepository mahaprasadEventRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalDayRepository festivalDayRepository;

    // =========================================================
    // GET ALL
    // =========================================================

    @Transactional(readOnly = true)
    public List<AdminMahaprasadResponse> getAll() {

        return mahaprasadEventRepository
                .findByDeletedAtIsNullOrderByEventDateDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // GET BY FESTIVAL
    // =========================================================

    @Transactional(readOnly = true)
    public List<AdminMahaprasadResponse> getByFestival(Long festivalId) {

        validateFestival(festivalId);

        return mahaprasadEventRepository
                .findByFestivalIdAndDeletedAtIsNullOrderByEventDateDesc(
                        festivalId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Transactional(readOnly = true)
    public AdminMahaprasadResponse getById(Long id) {

        MahaprasadEvent event = getExistingEvent(id);

        return mapToResponse(event);
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Transactional
    public AdminMahaprasadResponse create(
            AdminMahaprasadRequest request
    ) {

        validateFestival(request.getFestivalId());

        validateFestivalDay(
                request.getFestivalDayId(),
                request.getFestivalId()
        );

        validateTime(request);

        if (mahaprasadEventRepository
                .existsByTitleAndEventDateAndDeletedAtIsNull(
                        request.getTitle().trim(),
                        request.getEventDate()
                )) {

            throw new BusinessException(
                    "Mahaprasad event with this title and date already exists"
            );
        }

        MahaprasadEvent event = new MahaprasadEvent();

        event.setFestivalId(request.getFestivalId());
        event.setFestivalDayId(request.getFestivalDayId());
        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setLocation(request.getLocation().trim());
        event.setEstimatedPeople(request.getEstimatedPeople());

        /*
         * New admin records start as DRAFT.
         * They can later be activated.
         */
        event.setStatus("DRAFT");

        LocalDateTime now = LocalDateTime.now();

        event.setCreatedAt(now);
        event.setUpdatedAt(now);

        MahaprasadEvent saved =
                mahaprasadEventRepository.save(event);

        return mapToResponse(saved);
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Transactional
    public AdminMahaprasadResponse update(
            Long id,
            AdminMahaprasadRequest request
    ) {

        MahaprasadEvent event = getExistingEvent(id);

        validateFestival(request.getFestivalId());

        validateFestivalDay(
                request.getFestivalDayId(),
                request.getFestivalId()
        );

        validateTime(request);

        if (mahaprasadEventRepository
                .existsByTitleAndEventDateAndIdNotAndDeletedAtIsNull(
                        request.getTitle().trim(),
                        request.getEventDate(),
                        id
                )) {

            throw new BusinessException(
                    "Mahaprasad event with this title and date already exists"
            );
        }

        event.setFestivalId(request.getFestivalId());
        event.setFestivalDayId(request.getFestivalDayId());
        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setLocation(request.getLocation().trim());
        event.setEstimatedPeople(request.getEstimatedPeople());

        event.setUpdatedAt(LocalDateTime.now());

        MahaprasadEvent updated =
                mahaprasadEventRepository.save(event);

        return mapToResponse(updated);
    }

    // =========================================================
    // ACTIVATE
    // =========================================================

    @Transactional
    public AdminMahaprasadResponse activate(Long id) {

        MahaprasadEvent event = getExistingEvent(id);

        if ("ACTIVE".equalsIgnoreCase(event.getStatus())) {

            throw new BusinessException(
                    "Mahaprasad event is already active"
            );
        }

        event.setStatus("ACTIVE");
        event.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                mahaprasadEventRepository.save(event)
        );
    }

    // =========================================================
    // DEACTIVATE
    // =========================================================

    @Transactional
    public AdminMahaprasadResponse deactivate(Long id) {

        MahaprasadEvent event = getExistingEvent(id);

        if ("INACTIVE".equalsIgnoreCase(event.getStatus())) {

            throw new BusinessException(
                    "Mahaprasad event is already inactive"
            );
        }

        event.setStatus("INACTIVE");
        event.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                mahaprasadEventRepository.save(event)
        );
    }

    // =========================================================
    // SOFT DELETE
    // =========================================================

    @Transactional
    public void delete(Long id) {

        MahaprasadEvent event = getExistingEvent(id);

        event.setStatus("DELETED");
        event.setDeletedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());

        mahaprasadEventRepository.save(event);
    }

    // =========================================================
    // FIND EXISTING
    // =========================================================

    private MahaprasadEvent getExistingEvent(Long id) {

        return mahaprasadEventRepository
                .findById(id)
                .filter(event -> event.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mahaprasad event not found with id: " + id
                        )
                );
    }

    // =========================================================
    // FESTIVAL VALIDATION
    // =========================================================

    private void validateFestival(Long festivalId) {

        festivalRepository.findById(festivalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival not found with id: " + festivalId
                        )
                );
    }

    // =========================================================
    // FESTIVAL DAY VALIDATION
    // =========================================================

    private void validateFestivalDay(
            Long festivalDayId,
            Long festivalId
    ) {

        if (festivalDayId == null) {
            return;
        }

        FestivalDay festivalDay =
                festivalDayRepository.findById(festivalDayId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Festival day not found with id: "
                                                + festivalDayId
                                )
                        );

        if (!festivalDay.getFestivalId().equals(festivalId)) {

            throw new BusinessException(
                    "Festival day does not belong to the selected festival"
            );
        }
    }

    // =========================================================
    // TIME VALIDATION
    // =========================================================

    private void validateTime(
            AdminMahaprasadRequest request
    ) {

        if (request.getEndTime() != null
                && !request.getEndTime()
                .isAfter(request.getStartTime())) {

            throw new BusinessException(
                    "End time must be after start time"
            );
        }
    }

    // =========================================================
    // ENTITY -> RESPONSE
    // =========================================================

    private AdminMahaprasadResponse mapToResponse(
            MahaprasadEvent event
    ) {

        return AdminMahaprasadResponse.builder()
                .id(event.getId())
                .festivalId(event.getFestivalId())
                .festivalDayId(event.getFestivalDayId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .estimatedPeople(event.getEstimatedPeople())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }
}