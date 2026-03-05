package com.streamforge.video.controller;

import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import com.streamforge.video.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Video CRUD operations.
 */
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoResponseDTO> createVideo(@Valid @RequestBody VideoRequestDTO requestDTO) {
        VideoResponseDTO response = videoService.createVideo(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable UUID id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    @GetMapping
    public ResponseEntity<List<VideoResponseDTO>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> updateVideo(@PathVariable UUID id,
                                                         @Valid @RequestBody VideoRequestDTO requestDTO) {
        return ResponseEntity.ok(videoService.updateVideo(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VideoResponseDTO>> getVideosByType(@PathVariable VideoType type) {
        return ResponseEntity.ok(videoService.getVideosByType(type));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<VideoResponseDTO>> getVideosByCategory(@PathVariable VideoCategory category) {
        return ResponseEntity.ok(videoService.getVideosByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<VideoResponseDTO>> searchVideosByTitle(@RequestParam String title) {
        return ResponseEntity.ok(videoService.searchVideosByTitle(title));
    }

    @GetMapping("/director")
    public ResponseEntity<List<VideoResponseDTO>> getVideosByDirector(@RequestParam String name) {
        return ResponseEntity.ok(videoService.getVideosByDirector(name));
    }

    @GetMapping("/rating")
    public ResponseEntity<List<VideoResponseDTO>> getVideosByMinRating(@RequestParam Double min) {
        return ResponseEntity.ok(videoService.getVideosByMinRating(min));
    }
}
