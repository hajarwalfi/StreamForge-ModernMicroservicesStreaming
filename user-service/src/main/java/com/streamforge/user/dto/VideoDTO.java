package com.streamforge.user.dto;

import lombok.*;

import java.util.UUID;

/**
 * DTO representing video data received from video-service via OpenFeign.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDTO {

    private UUID id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private String type;
    private String category;
    private Double rating;
    private String director;
    private String cast;
}
