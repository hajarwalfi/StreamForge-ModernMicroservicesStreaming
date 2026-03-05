package com.streamforge.user.client;

import com.streamforge.user.dto.VideoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * OpenFeign client for communicating with video-service.
 * Uses Eureka service discovery to resolve the video-service URL.
 */
@FeignClient(name = "video-service", fallback = VideoClientFallback.class)
public interface VideoClient {

    @GetMapping("/api/videos/{id}")
    VideoDTO getVideoById(@PathVariable("id") UUID id);
}
