package com.streamforge.video.mapper;

import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.Video;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VideoMapperTest {

    private VideoMapper videoMapper;

    private static final UUID VIDEO_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        videoMapper = new VideoMapper();
    }

    @Test
    @DisplayName("Should map VideoRequestDTO to Video entity")
    void toEntity_Success() {
        VideoRequestDTO dto = VideoRequestDTO.builder()
                .title("Inception")
                .description("A mind-bending thriller")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .build();

        Video video = videoMapper.toEntity(dto);

        assertThat(video.getTitle()).isEqualTo("Inception");
        assertThat(video.getDescription()).isEqualTo("A mind-bending thriller");
        assertThat(video.getDuration()).isEqualTo(148);
        assertThat(video.getType()).isEqualTo(VideoType.FILM);
        assertThat(video.getCategory()).isEqualTo(VideoCategory.SCIENCE_FICTION);
        assertThat(video.getId()).isNull();
    }

    @Test
    @DisplayName("Should map Video entity to VideoResponseDTO")
    void toResponseDTO_Success() {
        Video video = Video.builder()
                .id(VIDEO_ID)
                .title("Inception")
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .build();

        VideoResponseDTO dto = videoMapper.toResponseDTO(video);

        assertThat(dto.getId()).isEqualTo(VIDEO_ID);
        assertThat(dto.getTitle()).isEqualTo("Inception");
        assertThat(dto.getType()).isEqualTo(VideoType.FILM);
    }

    @Test
    @DisplayName("Should update Video entity from DTO")
    void updateEntity_Success() {
        Video video = Video.builder()
                .id(VIDEO_ID)
                .title("Old Title")
                .type(VideoType.FILM)
                .category(VideoCategory.ACTION)
                .build();

        VideoRequestDTO dto = VideoRequestDTO.builder()
                .title("New Title")
                .type(VideoType.SERIE)
                .category(VideoCategory.DRAME)
                .build();

        videoMapper.updateEntity(video, dto);

        assertThat(video.getTitle()).isEqualTo("New Title");
        assertThat(video.getType()).isEqualTo(VideoType.SERIE);
        assertThat(video.getCategory()).isEqualTo(VideoCategory.DRAME);
        assertThat(video.getId()).isEqualTo(VIDEO_ID); // ID should not change
    }
}
