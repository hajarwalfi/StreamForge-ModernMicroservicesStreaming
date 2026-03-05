package com.streamforge.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * DTO for recording watch history.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistoryRequestDTO {

    @NotNull(message = "Video ID is required")
    private UUID videoId;

    @Min(value = 0, message = "Progress time must be >= 0")
    private Integer progressTime;

    private Boolean completed;
}
