package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.MahaprasadEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MahaprasadEventRepository
        extends JpaRepository<MahaprasadEvent, Long> {

    // =========================
    // EXISTING PUBLIC METHODS
    // =========================

    List<MahaprasadEvent>
    findByStatusAndDeletedAtIsNullOrderByEventDateAscStartTimeAsc(
            String status
    );

    List<MahaprasadEvent>
    findByFestivalIdAndStatusAndDeletedAtIsNullOrderByEventDateAscStartTimeAsc(
            Long festivalId,
            String status
    );

    List<MahaprasadEvent>
    findByFestivalDayIdAndStatusAndDeletedAtIsNullOrderByStartTimeAsc(
            Long festivalDayId,
            String status
    );

    List<MahaprasadEvent>
    findByEventDateAndStatusAndDeletedAtIsNullOrderByStartTimeAsc(
            LocalDate eventDate,
            String status
    );


    // =========================
    // ADMIN METHODS
    // =========================

    List<MahaprasadEvent>
    findByDeletedAtIsNullOrderByEventDateDesc();

    List<MahaprasadEvent>
    findByFestivalIdAndDeletedAtIsNullOrderByEventDateDesc(
            Long festivalId
    );

    boolean existsByTitleAndEventDateAndDeletedAtIsNull(
            String title,
            LocalDate eventDate
    );

    boolean existsByTitleAndEventDateAndIdNotAndDeletedAtIsNull(
            String title,
            LocalDate eventDate,
            Long id
    );
}