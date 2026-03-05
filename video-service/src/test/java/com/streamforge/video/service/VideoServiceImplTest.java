package com.streamforge.video.service;

import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.Video;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import com.streamforge.video.exception.ResourceNotFoundException;
import com.streamforge.video.mapper.VideoMapper;
import com.streamforge.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoServiceImpl videoService;

    private Video video;
    private VideoRequestDTO requestDTO;
    private VideoResponseDTO responseDTO;

    private static final UUID VIDEO_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID VIDEO_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID NON_EXISTENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000099");

    @BeforeEach
    void setUp() {
        video = Video.builder()
                .id(VIDEO_ID)
                .title("Inception")
                .description("A mind-bending thriller")
                .thumbnailUrl("https://example.com/inception.jpg")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast("Leonardo DiCaprio, Joseph Gordon-Levitt")
                .build();

        requestDTO = VideoRequestDTO.builder()
                .title("Inception")
                .description("A mind-bending thriller")
                .thumbnailUrl("https://example.com/inception.jpg")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast("Leonardo DiCaprio, Joseph Gordon-Levitt")
                .build();

        responseDTO = VideoResponseDTO.builder()
                .id(VIDEO_ID)
                .title("Inception")
                .description("A mind-bending thriller")
                .thumbnailUrl("https://example.com/inception.jpg")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast("Leonardo DiCaprio, Joseph Gordon-Levitt")
                .build();
    }

    @Test
    @DisplayName("Should create a video successfully")
    void createVideo_Success() {
        when(videoMapper.toEntity(requestDTO)).thenReturn(video);
        when(videoRepository.save(any(Video.class))).thenReturn(video);
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        VideoResponseDTO result = videoService.createVideo(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(VIDEO_ID);
        assertThat(result.getTitle()).isEqualTo("Inception");
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    @DisplayName("Should get video by ID successfully")
    void getVideoById_Success() {
        when(videoRepository.findById(VIDEO_ID)).thenReturn(Optional.of(video));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        VideoResponseDTO result = videoService.getVideoById(VIDEO_ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(VIDEO_ID);
        assertThat(result.getTitle()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("Should throw exception when video not found")
    void getVideoById_NotFound() {
        when(videoRepository.findById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoService.getVideoById(NON_EXISTENT_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Video not found with id: " + NON_EXISTENT_ID);
    }

    @Test
    @DisplayName("Should return all videos")
    void getAllVideos_Success() {
        Video video2 = Video.builder().id(VIDEO_ID_2).title("The Dark Knight").type(VideoType.FILM).category(VideoCategory.ACTION).build();
        VideoResponseDTO responseDTO2 = VideoResponseDTO.builder().id(VIDEO_ID_2).title("The Dark Knight").build();

        when(videoRepository.findAll()).thenReturn(Arrays.asList(video, video2));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);
        when(videoMapper.toResponseDTO(video2)).thenReturn(responseDTO2);

        List<VideoResponseDTO> result = videoService.getAllVideos();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Inception");
        assertThat(result.get(1).getTitle()).isEqualTo("The Dark Knight");
    }

    @Test
    @DisplayName("Should update video successfully")
    void updateVideo_Success() {
        VideoRequestDTO updateDTO = VideoRequestDTO.builder()
                .title("Inception Updated")
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .build();

        VideoResponseDTO updatedResponse = VideoResponseDTO.builder()
                .id(VIDEO_ID)
                .title("Inception Updated")
                .build();

        when(videoRepository.findById(VIDEO_ID)).thenReturn(Optional.of(video));
        when(videoRepository.save(any(Video.class))).thenReturn(video);
        when(videoMapper.toResponseDTO(video)).thenReturn(updatedResponse);

        VideoResponseDTO result = videoService.updateVideo(VIDEO_ID, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Inception Updated");
        verify(videoMapper, times(1)).updateEntity(video, updateDTO);
    }

    @Test
    @DisplayName("Should delete video successfully")
    void deleteVideo_Success() {
        when(videoRepository.existsById(VIDEO_ID)).thenReturn(true);
        doNothing().when(videoRepository).deleteById(VIDEO_ID);

        videoService.deleteVideo(VIDEO_ID);

        verify(videoRepository, times(1)).deleteById(VIDEO_ID);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent video")
    void deleteVideo_NotFound() {
        when(videoRepository.existsById(NON_EXISTENT_ID)).thenReturn(false);

        assertThatThrownBy(() -> videoService.deleteVideo(NON_EXISTENT_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Should return videos by type")
    void getVideosByType_Success() {
        when(videoRepository.findByType(VideoType.FILM)).thenReturn(List.of(video));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        List<VideoResponseDTO> result = videoService.getVideosByType(VideoType.FILM);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo(VideoType.FILM);
    }

    @Test
    @DisplayName("Should return videos by category")
    void getVideosByCategory_Success() {
        when(videoRepository.findByCategory(VideoCategory.SCIENCE_FICTION)).thenReturn(List.of(video));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        List<VideoResponseDTO> result = videoService.getVideosByCategory(VideoCategory.SCIENCE_FICTION);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo(VideoCategory.SCIENCE_FICTION);
    }

    @Test
    @DisplayName("Should search videos by title")
    void searchVideosByTitle_Success() {
        when(videoRepository.findByTitleContainingIgnoreCase("inception")).thenReturn(List.of(video));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        List<VideoResponseDTO> result = videoService.searchVideosByTitle("inception");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("Should return videos by minimum rating")
    void getVideosByMinRating_Success() {
        when(videoRepository.findByRatingGreaterThanEqual(8.0)).thenReturn(List.of(video));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        List<VideoResponseDTO> result = videoService.getVideosByMinRating(8.0);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRating()).isGreaterThanOrEqualTo(8.0);
    }
}
