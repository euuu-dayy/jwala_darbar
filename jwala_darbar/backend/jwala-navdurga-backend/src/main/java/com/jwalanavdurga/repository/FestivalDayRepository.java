package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.FestivalDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FestivalDayRepository extends JpaRepository<FestivalDay, Long> {

    List<FestivalDay> findByFestivalIdOrderByDayNumberAsc(Long festivalId);

    Optional<FestivalDay> findByFestivalIdAndFestivalDate(
            Long festivalId,
            LocalDate festivalDate
    );
    
    boolean existsByFestivalIdAndDayNumber(
            Long festivalId,
            Integer dayNumber
    );

    boolean existsByFestivalIdAndFestivalDate(
            Long festivalId,
            LocalDate festivalDate
    );

    boolean existsByFestivalIdAndDayNumberAndIdNot(
            Long festivalId,
            Integer dayNumber,
            Long id
    );

    boolean existsByFestivalIdAndFestivalDateAndIdNot(
            Long festivalId,
            LocalDate festivalDate,
            Long id
    );
}