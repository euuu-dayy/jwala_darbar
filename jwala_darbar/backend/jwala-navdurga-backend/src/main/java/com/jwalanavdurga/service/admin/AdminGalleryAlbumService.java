package com.jwalanavdurga.service.admin;

import com.jwalanavdurga.dto.admin.AdminGalleryAlbumRequest;
import com.jwalanavdurga.dto.admin.AdminGalleryAlbumResponse;
import com.jwalanavdurga.entity.GalleryAlbum;
import com.jwalanavdurga.exception.BusinessException;
import com.jwalanavdurga.exception.ResourceNotFoundException;
import com.jwalanavdurga.repository.FestivalDayRepository;
import com.jwalanavdurga.repository.FestivalRepository;
import com.jwalanavdurga.repository.GalleryAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGalleryAlbumService {

    private final GalleryAlbumRepository galleryRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalDayRepository festivalDayRepository;

    @Transactional(readOnly = true)
    public List<AdminGalleryAlbumResponse> getAll() {

        return galleryRepository
                .findByDeletedAtIsNullOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminGalleryAlbumResponse getById(Long id) {

        GalleryAlbum album = galleryRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Gallery album not found: " + id
                        ));

        return mapToResponse(album);
    }

    @Transactional(readOnly = true)
    public List<AdminGalleryAlbumResponse> getByFestivalId(
            Long festivalId) {

        return galleryRepository
                .findByFestivalIdAndDeletedAtIsNullOrderByDisplayOrderAsc(
                        festivalId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public AdminGalleryAlbumResponse create(
            AdminGalleryAlbumRequest request) {

        validateReferences(
                request.getFestivalId(),
                request.getFestivalDayId()
        );

        if (galleryRepository.existsByFestivalIdAndTitleAndDeletedAtIsNull(
                request.getFestivalId(),
                request.getTitle().trim()
        )) {
            throw new BusinessException(
                    "Gallery album with this title already exists"
            );
        }

        GalleryAlbum album = new GalleryAlbum();

        album.setFestivalId(request.getFestivalId());
        album.setFestivalDayId(request.getFestivalDayId());
        album.setTitle(request.getTitle().trim());
        album.setDescription(request.getDescription());
        album.setDriveUrl(request.getDriveUrl().trim());
        album.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );
        album.setStatus("ACTIVE");

        LocalDateTime now = LocalDateTime.now();

        album.setCreatedAt(now);
        album.setUpdatedAt(now);

        GalleryAlbum saved = galleryRepository.save(album);

        return mapToResponse(saved);
    }

    @Transactional
    public AdminGalleryAlbumResponse update(
            Long id,
            AdminGalleryAlbumRequest request) {

        GalleryAlbum album = galleryRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Gallery album not found: " + id
                        ));

        validateReferences(
                request.getFestivalId(),
                request.getFestivalDayId()
        );

        if (galleryRepository
                .existsByFestivalIdAndTitleAndIdNotAndDeletedAtIsNull(
                        request.getFestivalId(),
                        request.getTitle().trim(),
                        id
                )) {

            throw new BusinessException(
                    "Gallery album with this title already exists"
            );
        }

        album.setFestivalId(request.getFestivalId());
        album.setFestivalDayId(request.getFestivalDayId());
        album.setTitle(request.getTitle().trim());
        album.setDescription(request.getDescription());
        album.setDriveUrl(request.getDriveUrl().trim());

        album.setDisplayOrder(
                request.getDisplayOrder() != null
                        ? request.getDisplayOrder()
                        : 0
        );

        album.setUpdatedAt(LocalDateTime.now());

        GalleryAlbum updated = galleryRepository.save(album);

        return mapToResponse(updated);
    }

    @Transactional
    public void delete(Long id) {

        GalleryAlbum album = galleryRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Gallery album not found: " + id
                        ));

        LocalDateTime now = LocalDateTime.now();

        album.setDeletedAt(now);
        album.setStatus("DELETED");
        album.setUpdatedAt(now);

        galleryRepository.save(album);
    }

    @Transactional
    public AdminGalleryAlbumResponse changeStatus(
            Long id,
            String status) {

        GalleryAlbum album = galleryRepository
                .findById(id)
                .filter(a -> a.getDeletedAt() == null)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Gallery album not found: " + id
                        ));

        String normalizedStatus = status.trim().toUpperCase();

        if (!normalizedStatus.equals("ACTIVE")
                && !normalizedStatus.equals("INACTIVE")) {

            throw new BusinessException(
                    "Invalid gallery status. Allowed values: ACTIVE, INACTIVE"
            );
        }

        album.setStatus(normalizedStatus);
        album.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                galleryRepository.save(album)
        );
    }

    private void validateReferences(
            Long festivalId,
            Long festivalDayId) {

        if (festivalId == null) {
            throw new BusinessException("Festival ID is required");
        }

        if (!festivalRepository.existsById(festivalId)) {
            throw new ResourceNotFoundException(
                    "Festival not found: " + festivalId
            );
        }

        if (festivalDayId != null
                && !festivalDayRepository.existsById(festivalDayId)) {

            throw new ResourceNotFoundException(
                    "Festival day not found: " + festivalDayId
            );
        }
    }

    private AdminGalleryAlbumResponse mapToResponse(
            GalleryAlbum album) {

        return AdminGalleryAlbumResponse.builder()
                .id(album.getId())
                .festivalId(album.getFestivalId())
                .festivalDayId(album.getFestivalDayId())
                .title(album.getTitle())
                .description(album.getDescription())
                .driveUrl(album.getDriveUrl())
                .displayOrder(album.getDisplayOrder())
                .status(album.getStatus())
                .createdAt(album.getCreatedAt())
                .updatedAt(album.getUpdatedAt())
                .build();
    }
}