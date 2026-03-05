package com.streamforge.user.service;

import com.streamforge.user.dto.*;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for User business logic.
 */
public interface UserService {
    // User CRUD
    UserResponseDTO createUser(UserRequestDTO requestDTO);
    UserResponseDTO getUserById(UUID id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO);
    void deleteUser(UUID id);

    // Watchlist
    WatchlistResponseDTO addToWatchlist(UUID userId, UUID videoId);
    void removeFromWatchlist(UUID userId, UUID videoId);
    List<WatchlistResponseDTO> getUserWatchlist(UUID userId);

    // Watch History
    WatchHistoryResponseDTO recordWatchHistory(UUID userId, WatchHistoryRequestDTO requestDTO);
    List<WatchHistoryResponseDTO> getUserWatchHistory(UUID userId);

    // Statistics
    UserStatsDTO getUserStats(UUID userId);
}
