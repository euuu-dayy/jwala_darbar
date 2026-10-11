package com.jwalanavdurga.service;

import com.jwalanavdurga.dto.GalleryAlbumResponse;
import com.jwalanavdurga.entity.GalleryAlbum;
import com.jwalanavdurga.repository.GalleryAlbumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryAlbumService {

    private static final Logger log =
            LoggerFactory.getLogger(GalleryAlbumService.class);

    private final GalleryAlbumRepository galleryAlbumRepository;

    public GalleryAlbumService(
            GalleryAlbumRepository galleryAlbumRepository) {

        this.galleryAlbumRepository = galleryAlbumRepository;
    }

    public List<GalleryAlbumResponse> getAllGalleryAlbums() {

        log.info("Fetching all published gallery albums");

        List<GalleryAlbumResponse> albums =
                galleryAlbumRepository
                        .findByStatusAndDeletedAtIsNullOrderByDisplayOrderAsc(
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} published gallery albums",
                albums.size()
        );

        return albums;
    }

    public List<GalleryAlbumResponse> getGalleryByFestival(
            Long festivalId) {

        log.info(
                "Fetching gallery albums | festivalId={}",
                festivalId
        );

        List<GalleryAlbumResponse> albums =
                galleryAlbumRepository
                        .findByFestivalIdAndStatusAndDeletedAtIsNullOrderByDisplayOrderAsc(
                                festivalId,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} gallery albums | festivalId={}",
                albums.size(),
                festivalId
        );

        return albums;
    }

    public List<GalleryAlbumResponse> getGalleryByFestivalDay(
            Long festivalDayId) {

        log.info(
                "Fetching gallery albums | festivalDayId={}",
                festivalDayId
        );

        List<GalleryAlbumResponse> albums =
                galleryAlbumRepository
                        .findByFestivalDayIdAndStatusAndDeletedAtIsNullOrderByDisplayOrderAsc(
                                festivalDayId,
                                "PUBLISHED"
                        )
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info(
                "Fetched {} gallery albums | festivalDayId={}",
                albums.size(),
                festivalDayId
        );

        return albums;
    }

    private GalleryAlbumResponse mapToResponse(
            GalleryAlbum album) {

        GalleryAlbumResponse response =
                new GalleryAlbumResponse();

        response.setId(album.getId());
        response.setFestivalId(album.getFestivalId());
        response.setFestivalDayId(album.getFestivalDayId());
        response.setTitle(album.getTitle());
        response.setDescription(album.getDescription());
        response.setDriveUrl(album.getDriveUrl());
        response.setDisplayOrder(album.getDisplayOrder());
        response.setStatus(album.getStatus());

        return response;
    }
}