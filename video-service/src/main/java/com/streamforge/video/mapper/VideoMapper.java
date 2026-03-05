package com.streamforge.video.mapper;

import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.Video;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Video entity and DTOs.
 */
@Component
public class VideoMapper {

    /**
     * Converts a VideoRequestDTO to a Video entity.
     */
    public Video toEntity(VideoRequestDTO dto) {
        return Video.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .thumbnailUrl(dto.getThumbnailUrl())
                .trailerUrl(dto.getTrailerUrl())
                .duration(dto.getDuration())
                .releaseYear(dto.getReleaseYear())
                .type(dto.getType())
                .category(dto.getCategory())
                .rating(dto.getRating())
                .director(dto.getDirector())
                .cast(dto.getCast())
                .build();
    }

    /**
     * Converts a Video entity to a VideoResponseDTO.
     */
    public VideoResponseDTO toResponseDTO(Video video) {
        return VideoResponseDTO.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .thumbnailUrl(video.getThumbnailUrl())
                .trailerUrl(video.getTrailerUrl())
                .duration(video.getDuration())
                .releaseYear(video.getReleaseYear())
                .type(video.getType())
                .category(video.getCategory())
                .rating(video.getRating())
                .director(video.getDirector())
                .cast(video.getCast())
                .build();
    }

    /**
     * Updates an existing Video entity with data from a VideoRequestDTO.
     */
    public void updateEntity(Video video, VideoRequestDTO dto) {
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setTrailerUrl(dto.getTrailerUrl());
        video.setDuration(dto.getDuration());
        video.setReleaseYear(dto.getReleaseYear());
        video.setType(dto.getType());
        video.setCategory(dto.getCategory());
        video.setRating(dto.getRating());
        video.setDirector(dto.getDirector());
        video.setCast(dto.getCast());
    }
}
