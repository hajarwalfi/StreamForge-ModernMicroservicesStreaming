package com.streamforge.user.repository;

import com.streamforge.user.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for WatchHistory entity.
 */
@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, UUID> {

    List<WatchHistory> findByUserId(UUID userId);

    List<WatchHistory> findByUserIdOrderByWatchedAtDesc(UUID userId);

    List<WatchHistory> findByUserIdAndVideoId(UUID userId, UUID videoId);

    int countByUserId(UUID userId);

    int countByUserIdAndCompletedTrue(UUID userId);

    @Query("SELECT COALESCE(SUM(wh.progressTime), 0) FROM WatchHistory wh WHERE wh.user.id = :userId")
    int sumProgressTimeByUserId(@Param("userId") UUID userId);
}
