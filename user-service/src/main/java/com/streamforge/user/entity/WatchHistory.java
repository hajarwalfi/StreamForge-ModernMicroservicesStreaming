package com.streamforge.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a user's watch history entry.
 */
@Entity
@Table(name = "watch_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "video_id", nullable = false)
    private UUID videoId;

    @Column(nullable = false)
    private LocalDateTime watchedAt;

    @Column(nullable = false)
    private Integer progressTime;

    @Column(nullable = false)
    private Boolean completed;

    @PrePersist
    protected void onCreate() {
        this.watchedAt = LocalDateTime.now();
        if (this.completed == null) {
            this.completed = false;
        }
        if (this.progressTime == null) {
            this.progressTime = 0;
        }
    }
}
