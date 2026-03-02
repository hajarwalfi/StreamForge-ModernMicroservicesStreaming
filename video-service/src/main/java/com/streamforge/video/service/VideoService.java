package com.streamforge.video.service;

import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Video business logic.
 */
public interface VideoService {

    VideoResponseDTO createVideo(VideoRequestDTO requestDTO);

    VideoResponseDTO getVideoById(UUID id);

    List<VideoResponseDTO> getAllVideos();

    VideoResponseDTO updateVideo(UUID id, VideoRequestDTO requestDTO);

    void deleteVideo(UUID id);

    List<VideoResponseDTO> getVideosByType(VideoType type);

    List<VideoResponseDTO> getVideosByCategory(VideoCategory category);

    List<VideoResponseDTO> searchVideosByTitle(String title);

    List<VideoResponseDTO> getVideosByDirector(String director);

    List<VideoResponseDTO> getVideosByMinRating(Double rating);
}
