package com.streamforge.video.dto;

import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Data Transfer Object for Video requests (create/update).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String thumbnailUrl;

    private String trailerUrl;

    private Integer duration;

    private Integer releaseYear;

    @NotNull(message = "Type is required (FILM or SERIE)")
    private VideoType type;

    @NotNull(message = "Category is required")
    private VideoCategory category;

    private Double rating;

    private String director;

    private String cast;
}
