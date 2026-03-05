package com.streamforge.user.controller;

import com.streamforge.user.dto.WatchlistResponseDTO;
import com.streamforge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Watchlist operations.
 */
@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final UserService userService;

    @PostMapping("/{userId}/videos/{videoId}")
    public ResponseEntity<WatchlistResponseDTO> addToWatchlist(@PathVariable UUID userId,
                                                                @PathVariable UUID videoId) {
        WatchlistResponseDTO response = userService.addToWatchlist(userId, videoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{userId}/videos/{videoId}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable UUID userId,
                                                     @PathVariable UUID videoId) {
        userService.removeFromWatchlist(userId, videoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WatchlistResponseDTO>> getUserWatchlist(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserWatchlist(userId));
    }
}
