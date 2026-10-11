package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.AdminFestivalRequest;
import com.jwalanavdurga.dto.AdminFestivalResponse;
import com.jwalanavdurga.entity.Festival;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.FestivalRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminFestivalService {

    private final FestivalRepository festivalRepository;

    public AdminFestivalService(
            FestivalRepository festivalRepository) {

        this.festivalRepository = festivalRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminFestivalResponse> getAllFestivals() {

        return festivalRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminFestivalResponse getFestival(Long id) {

        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival not found with id: " + id
                        )
                );

        return mapToResponse(festival);
    }

    @Transactional
    public AdminFestivalResponse createFestival(
            AdminFestivalRequest request) {

        validateDates(request);

        if (festivalRepository.existsByYear(request.getYear())) {
            throw new BusinessException(
                    "Festival already exists for year "
                            + request.getYear()
            );
        }

        Festival festival = new Festival();

        festival.setName(request.getName().trim());
        festival.setYear(request.getYear());
        festival.setStartDate(request.getStartDate());
        festival.setEndDate(request.getEndDate());
        festival.setDescription(request.getDescription());
        festival.setStatus("DRAFT");

        LocalDateTime now = LocalDateTime.now();

        festival.setCreatedAt(now);
        festival.setUpdatedAt(now);

        return mapToResponse(
                festivalRepository.save(festival)
        );
    }

    @Transactional
    public AdminFestivalResponse updateFestival(
            Long id,
            AdminFestivalRequest request) {

        validateDates(request);

        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival not found with id: " + id
                        )
                );

        if (festivalRepository.existsByYearAndIdNot(
                request.getYear(), id)) {

            throw new BusinessException(
                    "Festival already exists for year "
                            + request.getYear()
            );
        }

        festival.setName(request.getName().trim());
        festival.setYear(request.getYear());
        festival.setStartDate(request.getStartDate());
        festival.setEndDate(request.getEndDate());
        festival.setDescription(request.getDescription());
        festival.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                festivalRepository.save(festival)
        );
    }

    @Transactional
    public void deleteFestival(Long id) {

        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival not found with id: " + id
                        )
                );

        if ("PUBLISHED".equalsIgnoreCase(festival.getStatus())) {
            throw new BusinessException(
                    "Published festival cannot be deleted"
            );
        }

        festival.setStatus("DELETED");
        festival.setUpdatedAt(LocalDateTime.now());

        festivalRepository.save(festival);
    }

    @Transactional
    public AdminFestivalResponse publishFestival(Long id) {

        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Festival not found with id: " + id
                        )
                );

        if ("DELETED".equalsIgnoreCase(festival.getStatus())) {
            throw new BusinessException(
                    "Deleted festival cannot be published"
            );
        }

        festival.setStatus("PUBLISHED");
        festival.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                festivalRepository.save(festival)
        );
    }

    private void validateDates(
            AdminFestivalRequest request) {

        if (request.getEndDate()
                .isBefore(request.getStartDate())) {

            throw new BusinessException(
                    "End date cannot be before start date"
            );
        }
    }

    private AdminFestivalResponse mapToResponse(
            Festival festival) {

        AdminFestivalResponse response =
                new AdminFestivalResponse();

        response.setId(festival.getId());
        response.setName(festival.getName());
        response.setYear(festival.getYear());
        response.setStartDate(festival.getStartDate());
        response.setEndDate(festival.getEndDate());
        response.setDescription(festival.getDescription());
        response.setStatus(festival.getStatus());
        response.setCreatedAt(festival.getCreatedAt());
        response.setUpdatedAt(festival.getUpdatedAt());

        return response;
    }
}