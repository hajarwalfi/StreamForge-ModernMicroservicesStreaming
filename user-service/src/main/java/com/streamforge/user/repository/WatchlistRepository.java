package com.streamforge.user.repository;

import com.streamforge.user.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Watchlist entity.
 */
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {

    List<Watchlist> findByUserId(UUID userId);

    Optional<Watchlist> findByUserIdAndVideoId(UUID userId, UUID videoId);

    boolean existsByUserIdAndVideoId(UUID userId, UUID videoId);

    void deleteByUserIdAndVideoId(UUID userId, UUID videoId);

    int countByUserId(UUID userId);
}
