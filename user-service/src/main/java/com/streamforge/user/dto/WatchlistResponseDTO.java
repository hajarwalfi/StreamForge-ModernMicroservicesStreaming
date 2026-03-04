package com.streamforge.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Watchlist responses (includes video details from video-service).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistResponseDTO {

    private UUID id;
    private UUID userId;
    private UUID videoId;
    private LocalDateTime addedAt;
    private VideoDTO video;
}
