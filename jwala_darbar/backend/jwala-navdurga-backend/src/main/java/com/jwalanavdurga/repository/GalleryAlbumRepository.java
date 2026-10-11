package com.jwalanavdurga.repository;

import com.jwalanavdurga.entity.GalleryAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryAlbumRepository
        extends JpaRepository<GalleryAlbum, Long> {

    // Public gallery
    List<GalleryAlbum> findByStatusAndDeletedAtIsNullOrderByDisplayOrderAsc(
            String status
    );

    List<GalleryAlbum> findByFestivalIdAndStatusAndDeletedAtIsNullOrderByDisplayOrderAsc(
            Long festivalId,
            String status
    );

    List<GalleryAlbum> findByFestivalDayIdAndStatusAndDeletedAtIsNullOrderByDisplayOrderAsc(
            Long festivalDayId,
            String status
    );

    // Admin gallery
    List<GalleryAlbum> findByFestivalIdAndDeletedAtIsNullOrderByDisplayOrderAsc(
            Long festivalId
    );

    List<GalleryAlbum> findByFestivalDayIdAndDeletedAtIsNullOrderByDisplayOrderAsc(
            Long festivalDayId
    );

    List<GalleryAlbum> findByDeletedAtIsNullOrderByDisplayOrderAsc();

    boolean existsByFestivalIdAndTitleAndDeletedAtIsNull(
            Long festivalId,
            String title
    );

    boolean existsByFestivalIdAndTitleAndIdNotAndDeletedAtIsNull(
            Long festivalId,
            String title,
            Long id
    );
}