package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.AdminFestivalDayRequest;
import com.jwalanavdurga.dto.AdminFestivalDayResponse;
import com.jwalanavdurga.entity.FestivalDay;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminFestivalDayService {

    private final FestivalDayRepository festivalDayRepository;
    private final FestivalRepository festivalRepository;

    public AdminFestivalDayService(
            FestivalDayRepository festivalDayRepository,
            FestivalRepository festivalRepository) {

        this.festivalDayRepository = festivalDayRepository;
        this.festivalRepository = festivalRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminFestivalDayResponse> getDays(
            Long festivalId) {

        validateFestivalExists(festivalId);

        return festivalDayRepository
                .findByFestivalIdOrderByDayNumberAsc(festivalId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminFestivalDayResponse getDay(
            Long festivalId,
            Long dayId) {

        FestivalDay day = findDay(festivalId, dayId);

        return mapToResponse(day);
    }

    @Transactional
    public AdminFestivalDayResponse createDay(
            Long festivalId,
            AdminFestivalDayRequest request) {

        validateFestivalExists(festivalId);

        validateDayDate(
                festivalId,
                request.getDayNumber(),
                request.getFestivalDate()
        );

        FestivalDay day = new FestivalDay();

        day.setFestivalId(festivalId);
        day.setDayNumber(request.getDayNumber());
        day.setFestivalDate(request.getFestivalDate());
        day.setTitle(request.getTitle().trim());
        day.setDescription(request.getDescription());
        day.setIsVisarjanDay(
                Boolean.TRUE.equals(request.getVisarjanDay())
        );

        LocalDateTime now = LocalDateTime.now();

        day.setCreatedAt(now);
        day.setUpdatedAt(now);

        return mapToResponse(
                festivalDayRepository.save(day)
        );
    }

    @Transactional
    public AdminFestivalDayResponse updateDay(
            Long festivalId,
            Long dayId,
            AdminFestivalDayRequest request) {

        FestivalDay day = findDay(festivalId, dayId);

        validateDayDateForUpdate(
                festivalId,
                dayId,
                request.getDayNumber(),
                request.getFestivalDate()
        );

        day.setDayNumber(request.getDayNumber());
        day.setFestivalDate(request.getFestivalDate());
        day.setTitle(request.getTitle().trim());
        day.setDescription(request.getDescription());
        day.setIsVisarjanDay(
                Boolean.TRUE.equals(request.getVisarjanDay())
        );
        day.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                festivalDayRepository.save(day)
        );
    }

    @Transactional
    public void deleteDay(
            Long festivalId,
            Long dayId) {

        FestivalDay day = findDay(festivalId, dayId);

        festivalDayRepository.delete(day);
    }

    private void validateFestivalExists(
            Long festivalId) {

        if (!festivalRepository.existsById(festivalId)) {
            throw new ResourceNotFoundException(
                    "Festival not found with id: " + festivalId
            );
        }
    }

    private FestivalDay findDay(
            Long festivalId,
            Long dayId) {

        validateFestivalExists(festivalId);

        FestivalDay day = festivalDayRepository
                .findById(dayId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival day not found with id: "
                                        + dayId
                        )
                );

        if (!day.getFestivalId().equals(festivalId)) {
            throw new ResourceNotFoundException(
                    "Festival day does not belong to this festival"
            );
        }

        return day;
    }

    private void validateDayDate(
            Long festivalId,
            Integer dayNumber,
            LocalDate festivalDate) {

        if (festivalDayRepository
                .existsByFestivalIdAndDayNumber(
                        festivalId,
                        dayNumber
                )) {

            throw new BusinessException(
                    "Day number already exists for this festival"
            );
        }

        if (festivalDayRepository
                .existsByFestivalIdAndFestivalDate(
                        festivalId,
                        festivalDate
                )) {

            throw new BusinessException(
                    "Festival date already exists for this festival"
            );
        }
    }

    private void validateDayDateForUpdate(
            Long festivalId,
            Long dayId,
            Integer dayNumber,
            LocalDate festivalDate) {

        if (festivalDayRepository
                .existsByFestivalIdAndDayNumberAndIdNot(
                        festivalId,
                        dayNumber,
                        dayId
                )) {

            throw new BusinessException(
                    "Day number already exists for this festival"
            );
        }

        if (festivalDayRepository
                .existsByFestivalIdAndFestivalDateAndIdNot(
                        festivalId,
                        festivalDate,
                        dayId
                )) {

            throw new BusinessException(
                    "Festival date already exists for this festival"
            );
        }
    }

    private AdminFestivalDayResponse mapToResponse(
            FestivalDay day) {

        AdminFestivalDayResponse response =
                new AdminFestivalDayResponse();

        response.setId(day.getId());
        response.setFestivalId(day.getFestivalId());
        response.setDayNumber(day.getDayNumber());
        response.setFestivalDate(day.getFestivalDate());
        response.setTitle(day.getTitle());
        response.setDescription(day.getDescription());
        response.setVisarjanDay(day.getIsVisarjanDay());
        response.setCreatedAt(day.getCreatedAt());
        response.setUpdatedAt(day.getUpdatedAt());

        return response;
    }
}