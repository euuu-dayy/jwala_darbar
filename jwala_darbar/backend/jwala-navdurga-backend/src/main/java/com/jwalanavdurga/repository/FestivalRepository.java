package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

    Optional<Festival> findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String status,
            LocalDate date1,
            LocalDate date2
    );
    
    boolean existsByYear(Integer year);

    boolean existsByYearAndIdNot(Integer year, Long id);
}