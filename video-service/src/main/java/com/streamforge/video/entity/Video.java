package com.streamforge.video.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entity representing a video content item.
 */
@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String thumbnailUrl;

    private String trailerUrl;

    private Integer duration;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoCategory category;

    private Double rating;

    private String director;

    @Column(name = "video_cast", length = 1000)
    private String cast;
}
