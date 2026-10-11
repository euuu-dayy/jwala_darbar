package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.FestivalDayTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FestivalDayThemeRepository
        extends JpaRepository<FestivalDayTheme, Long> {

    Optional<FestivalDayTheme> findByFestivalDayId(Long festivalDayId);

    boolean existsByFestivalDayId(Long festivalDayId);

    boolean existsByFestivalDayIdAndIdNot(Long festivalDayId, Long id);
}