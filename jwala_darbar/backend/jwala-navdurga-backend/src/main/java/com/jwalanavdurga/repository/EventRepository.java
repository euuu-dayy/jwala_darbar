package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByFestivalDayIdAndStatusOrderByStartTimeAsc(
            Long festivalDayId,
            String status
    );

    List<Event> findByFestivalDayIdAndEventTypeAndStatusOrderByStartTimeAsc(
            Long festivalDayId,
            String eventType,
            String status
    );
}