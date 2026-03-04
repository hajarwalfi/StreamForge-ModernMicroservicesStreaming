package com.streamforge.user.client;

import com.streamforge.user.dto.VideoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Fallback implementation for VideoClient when video-service is unavailable.
 */
@Component
@Slf4j
public class VideoClientFallback implements VideoClient {

    @Override
    public VideoDTO getVideoById(UUID id) {
        log.warn("Video service is unavailable. Returning fallback for video id: {}", id);
        return VideoDTO.builder()
                .id(id)
                .title("Video Unavailable")
                .description("Video service is currently unavailable")
                .build();
    }
}
