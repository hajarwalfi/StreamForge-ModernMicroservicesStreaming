package com.streamforge.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Watch History responses (includes video details from video-service).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistoryResponseDTO {

    private UUID id;
    private UUID userId;
    private UUID videoId;
    private LocalDateTime watchedAt;
    private Integer progressTime;
    private Boolean completed;
    private VideoDTO video;
}
