package com.streamforge.video.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamforge.video.dto.VideoRequestDTO;
import com.streamforge.video.dto.VideoResponseDTO;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import com.streamforge.video.exception.GlobalExceptionHandler;
import com.streamforge.video.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VideoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController videoController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private VideoRequestDTO requestDTO;
    private VideoResponseDTO responseDTO;

    private static final UUID VIDEO_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(videoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        requestDTO = VideoRequestDTO.builder()
                .title("Inception")
                .description("A mind-bending thriller")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast("Leonardo DiCaprio")
                .build();

        responseDTO = VideoResponseDTO.builder()
                .id(VIDEO_ID)
                .title("Inception")
                .description("A mind-bending thriller")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast("Leonardo DiCaprio")
                .build();
    }

    @Test
    @DisplayName("POST /api/videos - Create video")
    void createVideo() throws Exception {
        when(videoService.createVideo(any(VideoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(VIDEO_ID.toString()))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.type").value("FILM"));
    }

    @Test
    @DisplayName("GET /api/videos/{id} - Get video by ID")
    void getVideoById() throws Exception {
        when(videoService.getVideoById(VIDEO_ID)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/videos/" + VIDEO_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VIDEO_ID.toString()))
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    @DisplayName("GET /api/videos - Get all videos")
    void getAllVideos() throws Exception {
        when(videoService.getAllVideos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Inception"));
    }

    @Test
    @DisplayName("PUT /api/videos/{id} - Update video")
    void updateVideo() throws Exception {
        when(videoService.updateVideo(eq(VIDEO_ID), any(VideoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/videos/" + VIDEO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    @DisplayName("DELETE /api/videos/{id} - Delete video")
    void deleteVideo() throws Exception {
        mockMvc.perform(delete("/api/videos/" + VIDEO_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/videos - Validation error (missing title)")
    void createVideo_ValidationError() throws Exception {
        VideoRequestDTO invalidDTO = VideoRequestDTO.builder()
                .type(VideoType.FILM)
                .category(VideoCategory.ACTION)
                .build();

        mockMvc.perform(post("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/videos/type/{type} - Get videos by type")
    void getVideosByType() throws Exception {
        when(videoService.getVideosByType(VideoType.FILM)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/videos/type/FILM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("FILM"));
    }

    @Test
    @DisplayName("GET /api/videos/category/{category} - Get videos by category")
    void getVideosByCategory() throws Exception {
        when(videoService.getVideosByCategory(VideoCategory.SCIENCE_FICTION)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/videos/category/SCIENCE_FICTION"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("SCIENCE_FICTION"));
    }

    @Test
    @DisplayName("GET /api/videos/search?title= - Search videos")
    void searchVideosByTitle() throws Exception {
        when(videoService.searchVideosByTitle("inception")).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/videos/search").param("title", "inception"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"));
    }
}
