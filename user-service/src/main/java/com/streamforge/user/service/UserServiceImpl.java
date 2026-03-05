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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of UserService with full CRUD, watchlist, history, and stats.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WatchlistRepository watchlistRepository;
    private final WatchHistoryRepository watchHistoryRepository;
    private final VideoClient videoClient;
    private final UserMapper userMapper;

    // ==================== User CRUD ====================

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        log.info("Creating user: {}", requestDTO.getUsername());

        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already taken: " + requestDTO.getUsername());
        }
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered: " + requestDTO.getEmail());
        }

        User user = userMapper.toEntity(requestDTO);
        User savedUser = userRepository.save(user);
        log.info("User created with id: {}", savedUser.getId());
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        log.info("Fetching user with id: {}", id);
        User user = findUserById(id);
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO) {
        log.info("Updating user with id: {}", id);
        User user = findUserById(id);
        userMapper.updateEntity(user, requestDTO);
        User updatedUser = userRepository.save(user);
        log.info("User updated with id: {}", updatedUser.getId());
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
        log.info("User deleted with id: {}", id);
    }

    // ==================== Watchlist ====================

    @Override
    public WatchlistResponseDTO addToWatchlist(UUID userId, UUID videoId) {
        log.info("Adding video {} to watchlist for user {}", videoId, userId);
        User user = findUserById(userId);

        // Verify video exists via OpenFeign
        VideoDTO videoDTO = videoClient.getVideoById(videoId);

        if (watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new ResourceAlreadyExistsException(
                    String.format("Video %s is already in user %s's watchlist", videoId, userId));
        }

        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .videoId(videoId)
                .build();
        Watchlist saved = watchlistRepository.save(watchlist);
        log.info("Video {} added to watchlist for user {}", videoId, userId);

        return userMapper.toWatchlistResponseDTO(saved, videoDTO);
    }

    @Override
    public void removeFromWatchlist(UUID userId, UUID videoId) {
        log.info("Removing video {} from watchlist for user {}", videoId, userId);
        findUserById(userId);

        if (!watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new ResourceNotFoundException(
                    String.format("Video %s not found in user %s's watchlist", videoId, userId));
        }

        watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
        log.info("Video {} removed from watchlist for user {}", videoId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchlistResponseDTO> getUserWatchlist(UUID userId) {
        log.info("Fetching watchlist for user {}", userId);
        findUserById(userId);

        return watchlistRepository.findByUserId(userId).stream()
                .map(item -> {
                    VideoDTO videoDTO = videoClient.getVideoById(item.getVideoId());
                    return userMapper.toWatchlistResponseDTO(item, videoDTO);
                })
                .collect(Collectors.toList());
    }

    // ==================== Watch History ====================

    @Override
    public WatchHistoryResponseDTO recordWatchHistory(UUID userId, WatchHistoryRequestDTO requestDTO) {
        log.info("Recording watch history for user {} - video {}", userId, requestDTO.getVideoId());
        User user = findUserById(userId);

        // Verify video exists via OpenFeign
        VideoDTO videoDTO = videoClient.getVideoById(requestDTO.getVideoId());

        WatchHistory history = WatchHistory.builder()
                .user(user)
                .videoId(requestDTO.getVideoId())
                .progressTime(requestDTO.getProgressTime() != null ? requestDTO.getProgressTime() : 0)
                .completed(requestDTO.getCompleted() != null ? requestDTO.getCompleted() : false)
                .build();

        WatchHistory saved = watchHistoryRepository.save(history);
        log.info("Watch history recorded with id: {}", saved.getId());

        return userMapper.toWatchHistoryResponseDTO(saved, videoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryResponseDTO> getUserWatchHistory(UUID userId) {
        log.info("Fetching watch history for user {}", userId);
        findUserById(userId);

        return watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId).stream()
                .map(item -> {
                    VideoDTO videoDTO = videoClient.getVideoById(item.getVideoId());
                    return userMapper.toWatchHistoryResponseDTO(item, videoDTO);
                })
                .collect(Collectors.toList());
    }

    // ==================== Statistics ====================

    @Override
    @Transactional(readOnly = true)
    public UserStatsDTO getUserStats(UUID userId) {
        log.info("Fetching stats for user {}", userId);
        User user = findUserById(userId);

        int totalWatched = watchHistoryRepository.countByUserId(userId);
        int completedVideos = watchHistoryRepository.countByUserIdAndCompletedTrue(userId);
        int totalWatchTime = watchHistoryRepository.sumProgressTimeByUserId(userId);
        int watchlistSize = watchlistRepository.countByUserId(userId);
        double completionRate = totalWatched > 0 ? (double) completedVideos / totalWatched * 100 : 0;

        return UserStatsDTO.builder()
                .userId(userId)
                .username(user.getUsername())
                .totalVideosWatched(totalWatched)
                .totalWatchTimeMinutes(totalWatchTime)
                .completedVideos(completedVideos)
                .watchlistSize(watchlistSize)
                .completionRate(Math.round(completionRate * 100.0) / 100.0)
                .build();
    }

    // ==================== Helper ====================

    private User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}
