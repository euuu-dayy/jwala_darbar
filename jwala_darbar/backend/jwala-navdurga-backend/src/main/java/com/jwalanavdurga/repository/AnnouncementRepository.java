package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AnnouncementRepository
        extends JpaRepository<Announcement, Long> {

    // =========================================================
    // EXISTING PUBLIC ANNOUNCEMENT QUERIES
    // =========================================================

    List<Announcement> findByStatusAndDeletedAtIsNullOrderByPriorityAscPublishedAtDesc(
            String status
    );

    List<Announcement> findByFestivalIdAndStatusAndDeletedAtIsNullOrderByPriorityAscPublishedAtDesc(
            Long festivalId,
            String status
    );

    @Query("""
        SELECT a
        FROM Announcement a
        WHERE a.status = :status
          AND a.deletedAt IS NULL
          AND a.publishedAt <= :currentTime
          AND (
                a.expiresAt IS NULL
                OR a.expiresAt >= :currentTime
              )
        ORDER BY a.priority ASC, a.publishedAt DESC
        """)
    List<Announcement> findActiveAnnouncements(
            @Param("status") String status,
            @Param("currentTime") LocalDateTime currentTime
    );


    // =========================================================
    // ADMIN ANNOUNCEMENT QUERIES
    // =========================================================

    /**
     * Get all non-deleted announcements for admin.
     *
     * Unlike public APIs, this does NOT filter by status,
     * publishedAt or expiresAt.
     */
    List<Announcement> findByDeletedAtIsNullOrderByPublishedAtDesc();


    /**
     * Get all non-deleted announcements for a festival.
     */
    List<Announcement> findByFestivalIdAndDeletedAtIsNullOrderByPublishedAtDesc(
            Long festivalId
    );


    /**
     * Get announcements by status for admin.
     */
    List<Announcement> findByStatusAndDeletedAtIsNullOrderByPublishedAtDesc(
            String status
    );


    /**
     * Prevent duplicate announcement titles.
     */
    boolean existsByTitleAndDeletedAtIsNull(
            String title
    );


    /**
     * Prevent duplicate title while updating an existing announcement.
     */
    boolean existsByTitleAndIdNotAndDeletedAtIsNull(
            String title,
            Long id
    );
}