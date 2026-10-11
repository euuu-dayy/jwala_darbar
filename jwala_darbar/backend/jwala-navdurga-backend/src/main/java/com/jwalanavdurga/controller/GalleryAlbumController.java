package com.jwalanavdurga.controller;

import com.jwalanavdurga.dto.GalleryAlbumResponse;
import com.jwalanavdurga.service.GalleryAlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/gallery")
public class GalleryAlbumController {

    private final GalleryAlbumService galleryAlbumService;

    public GalleryAlbumController(GalleryAlbumService galleryAlbumService) {
        this.galleryAlbumService = galleryAlbumService;
    }

    @GetMapping
    public List<GalleryAlbumResponse> getAllGalleryAlbums() {
        return galleryAlbumService.getAllGalleryAlbums();
    }

    @GetMapping("/festival/{festivalId}")
    public List<GalleryAlbumResponse> getGalleryByFestival(
            @PathVariable Long festivalId) {

        return galleryAlbumService.getGalleryByFestival(festivalId);
    }

    @GetMapping("/day/{festivalDayId}")
    public List<GalleryAlbumResponse> getGalleryByFestivalDay(
            @PathVariable Long festivalDayId) {

        return galleryAlbumService.getGalleryByFestivalDay(festivalDayId);
    }
}