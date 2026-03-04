package com.streamforge.user.service;

import com.streamforge.user.client.VideoClient;
import com.streamforge.user.dto.*;
import com.streamforge.user.entity.User;
import com.streamforge.user.entity.WatchHistory;
import com.streamforge.user.entity.Watchlist;
import com.streamforge.user.exception.ResourceAlreadyExistsException;
import com.streamforge.user.exception.ResourceNotFoundException;
import com.streamforge.user.mapper.UserMapper;
import com.streamforge.user.repository.UserRepository;
import com.streamforge.user.repository.WatchHistoryRepository;
import com.streamforge.user.repository.WatchlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private WatchlistRepository watchlistRepository;
    @Mock
    private WatchHistoryRepository watchHistoryRepository;
    @Mock
    private VideoClient videoClient;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private VideoDTO videoDTO;

    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID VIDEO_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID)
                .username("john_doe")
                .email("john@example.com")
                .password("password123")
                .build();

        userRequestDTO = UserRequestDTO.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("password123")
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .id(USER_ID)
                .username("john_doe")
                .email("john@example.com")
                .build();

        videoDTO = VideoDTO.builder()
                .id(VIDEO_ID)
                .title("Inception")
                .type("FILM")
                .category("SCIENCE_FICTION")
                .build();
    }

    // ==================== User CRUD Tests ====================

    @Test
    @DisplayName("Should create user successfully")
    void createUser_Success() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userMapper.toEntity(userRequestDTO)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john_doe");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception for duplicate username")
    void createUser_DuplicateUsername() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(userRequestDTO))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Username already taken");
    }

    @Test
    @DisplayName("Should throw exception for duplicate email")
    void createUser_DuplicateEmail() {
        when(userRepository.existsByUsername("john_doe")).thenReturn(false);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(userRequestDTO))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Email already registered");
    }

    @Test
    @DisplayName("Should get user by ID")
    void getUserById_Success() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.getUserById(USER_ID);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(USER_ID);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void getUserById_NotFound() {
        UUID nonExistentId = UUID.fromString("00000000-0000-0000-0000-000000000099");
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Should get all users")
    void getAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Should delete user successfully")
    void deleteUser_Success() {
        when(userRepository.existsById(USER_ID)).thenReturn(true);

        userService.deleteUser(USER_ID);

        verify(userRepository, times(1)).deleteById(USER_ID);
    }

    // ==================== Watchlist Tests ====================

    @Test
    @DisplayName("Should add video to watchlist")
    void addToWatchlist_Success() {
        Watchlist watchlist = Watchlist.builder()
                .id(UUID.randomUUID()).user(user).videoId(VIDEO_ID).addedAt(LocalDateTime.now()).build();
        WatchlistResponseDTO watchlistResponse = WatchlistResponseDTO.builder()
                .id(UUID.randomUUID()).userId(USER_ID).videoId(VIDEO_ID).video(videoDTO).build();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(videoClient.getVideoById(VIDEO_ID)).thenReturn(videoDTO);
        when(watchlistRepository.existsByUserIdAndVideoId(USER_ID, VIDEO_ID)).thenReturn(false);
        when(watchlistRepository.save(any(Watchlist.class))).thenReturn(watchlist);
        when(userMapper.toWatchlistResponseDTO(watchlist, videoDTO)).thenReturn(watchlistResponse);

        WatchlistResponseDTO result = userService.addToWatchlist(USER_ID, VIDEO_ID);

        assertThat(result).isNotNull();
        assertThat(result.getVideoId()).isEqualTo(VIDEO_ID);
        assertThat(result.getVideo().getTitle()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("Should throw exception for duplicate watchlist entry")
    void addToWatchlist_AlreadyExists() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(videoClient.getVideoById(VIDEO_ID)).thenReturn(videoDTO);
        when(watchlistRepository.existsByUserIdAndVideoId(USER_ID, VIDEO_ID)).thenReturn(true);

        assertThatThrownBy(() -> userService.addToWatchlist(USER_ID, VIDEO_ID))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    // ==================== Watch History Tests ====================

    @Test
    @DisplayName("Should record watch history")
    void recordWatchHistory_Success() {
        WatchHistoryRequestDTO historyRequest = WatchHistoryRequestDTO.builder()
                .videoId(VIDEO_ID).progressTime(45).completed(false).build();
        WatchHistory history = WatchHistory.builder()
                .id(UUID.randomUUID()).user(user).videoId(VIDEO_ID).watchedAt(LocalDateTime.now())
                .progressTime(45).completed(false).build();
        WatchHistoryResponseDTO historyResponse = WatchHistoryResponseDTO.builder()
                .id(UUID.randomUUID()).userId(USER_ID).videoId(VIDEO_ID).progressTime(45).completed(false)
                .video(videoDTO).build();

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(videoClient.getVideoById(VIDEO_ID)).thenReturn(videoDTO);
        when(watchHistoryRepository.save(any(WatchHistory.class))).thenReturn(history);
        when(userMapper.toWatchHistoryResponseDTO(history, videoDTO)).thenReturn(historyResponse);

        WatchHistoryResponseDTO result = userService.recordWatchHistory(USER_ID, historyRequest);

        assertThat(result).isNotNull();
        assertThat(result.getProgressTime()).isEqualTo(45);
        assertThat(result.getCompleted()).isFalse();
    }

    // ==================== Stats Tests ====================

    @Test
    @DisplayName("Should return user stats")
    void getUserStats_Success() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(watchHistoryRepository.countByUserId(USER_ID)).thenReturn(10);
        when(watchHistoryRepository.countByUserIdAndCompletedTrue(USER_ID)).thenReturn(7);
        when(watchHistoryRepository.sumProgressTimeByUserId(USER_ID)).thenReturn(1500);
        when(watchlistRepository.countByUserId(USER_ID)).thenReturn(5);

        UserStatsDTO result = userService.getUserStats(USER_ID);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getTotalVideosWatched()).isEqualTo(10);
        assertThat(result.getCompletedVideos()).isEqualTo(7);
        assertThat(result.getTotalWatchTimeMinutes()).isEqualTo(1500);
        assertThat(result.getWatchlistSize()).isEqualTo(5);
        assertThat(result.getCompletionRate()).isEqualTo(70.0);
    }
}
