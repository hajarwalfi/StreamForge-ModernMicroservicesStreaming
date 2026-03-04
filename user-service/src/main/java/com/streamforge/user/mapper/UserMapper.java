package com.streamforge.user.mapper;

import com.streamforge.user.dto.*;
import com.streamforge.user.entity.User;
import com.streamforge.user.entity.WatchHistory;
import com.streamforge.user.entity.Watchlist;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User-related entities and DTOs.
 */
@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public void updateEntity(User user, UserRequestDTO dto) {
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
    }

    public WatchlistResponseDTO toWatchlistResponseDTO(Watchlist watchlist, VideoDTO videoDTO) {
        return WatchlistResponseDTO.builder()
                .id(watchlist.getId())
                .userId(watchlist.getUser().getId())
                .videoId(watchlist.getVideoId())
                .addedAt(watchlist.getAddedAt())
                .video(videoDTO)
                .build();
    }

    public WatchHistoryResponseDTO toWatchHistoryResponseDTO(WatchHistory history, VideoDTO videoDTO) {
        return WatchHistoryResponseDTO.builder()
                .id(history.getId())
                .userId(history.getUser().getId())
                .videoId(history.getVideoId())
                .watchedAt(history.getWatchedAt())
                .progressTime(history.getProgressTime())
                .completed(history.getCompleted())
                .video(videoDTO)
                .build();
    }
}
