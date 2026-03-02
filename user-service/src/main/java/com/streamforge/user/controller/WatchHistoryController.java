package com.streamforge.user.controller;

import com.streamforge.user.dto.WatchHistoryRequestDTO;
import com.streamforge.user.dto.WatchHistoryResponseDTO;
import com.streamforge.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Watch History operations.
 */
@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<WatchHistoryResponseDTO> recordWatchHistory(
            @PathVariable UUID userId,
            @Valid @RequestBody WatchHistoryRequestDTO requestDTO) {
        WatchHistoryResponseDTO response = userService.recordWatchHistory(userId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WatchHistoryResponseDTO>> getUserWatchHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserWatchHistory(userId));
    }
}
