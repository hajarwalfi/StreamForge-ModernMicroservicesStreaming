package com.streamforge.video.service;

import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.Video;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import com.streamforge.video.exception.ResourceNotFoundException;
import com.streamforge.video.mapper.VideoMapper;
import com.streamforge.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of VideoService with full CRUD and search capabilities.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    public VideoResponseDTO createVideo(VideoRequestDTO requestDTO) {
        log.info("Creating new video: {}", requestDTO.getTitle());
        Video video = videoMapper.toEntity(requestDTO);
        Video savedVideo = videoRepository.save(video);
        log.info("Video created with id: {}", savedVideo.getId());
        return videoMapper.toResponseDTO(savedVideo);
    }

    @Override
    @Transactional(readOnly = true)
    public VideoResponseDTO getVideoById(UUID id) {
        log.info("Fetching video with id: {}", id);
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video", id));
        return videoMapper.toResponseDTO(video);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponseDTO> getAllVideos() {
        log.info("Fetching all videos");
        return videoRepository.findAll().stream()
                .map(videoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VideoResponseDTO updateVideo(UUID id, VideoRequestDTO requestDTO) {
        log.info("Updating video with id: {}", id);
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video", id));
        videoMapper.updateEntity(video, requestDTO);
        Video updatedVideo = videoRepository.save(video);
        log.info("Video updated with id: {}", updatedVideo.getId());
        return videoMapper.toResponseDTO(updatedVideo);
    }

    @Override
    public void deleteVideo(UUID id) {
        log.info("Deleting video with id: {}", id);
        if (!videoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Video", id);
        }
        videoRepository.deleteById(id);
        log.info("Video deleted with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponseDTO> getVideosByType(VideoType type) {
        log.info("Fetching videos by type: {}", type);
        return videoRepository.findByType(type).stream()
                .map(videoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponseDTO> getVideosByCategory(VideoCategory category) {
        log.info("Fetching videos by category: {}", category);
        return videoRepository.findByCategory(category).stream()
                .map(videoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponseDTO> searchVideosByTitle(String title) {
        log.info("Searching videos by title: {}", title);
        return videoRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(videoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponseDTO> getVideosByDirector(String director) {
        log.info("Fetching videos by director: {}", director);
        return videoRepository.findByDirectorContainingIgnoreCase(director).stream()
                .map(videoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponseDTO> getVideosByMinRating(Double rating) {
        log.info("Fetching videos with rating >= {}", rating);
        return videoRepository.findByRatingGreaterThanEqual(rating).stream()
                .map(videoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
