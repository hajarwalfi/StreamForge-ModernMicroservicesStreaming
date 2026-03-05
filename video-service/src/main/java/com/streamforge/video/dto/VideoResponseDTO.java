package com.streamforge.video.dto;

import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import lombok.*;

import java.util.UUID;

/**
 * Data Transfer Object for Video responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private VideoType type;
    private VideoCategory category;
    private Double rating;
    private String director;
    private String cast;
}
