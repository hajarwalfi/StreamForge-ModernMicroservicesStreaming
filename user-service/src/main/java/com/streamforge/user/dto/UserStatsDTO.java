package com.streamforge.user.dto;

import lombok.*;

import java.util.UUID;

/**
 * DTO for user viewing statistics.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatsDTO {

    private UUID userId;
    private String username;
    private int totalVideosWatched;
    private int totalWatchTimeMinutes;
    private int completedVideos;
    private int watchlistSize;
    private double completionRate;
}
