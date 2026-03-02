package com.streamforge.video.repository;

import com.streamforge.video.entity.Video;
import com.streamforge.video.entity.VideoCategory;
import com.streamforge.video.entity.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Video entity CRUD operations.
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {

    List<Video> findByType(VideoType type);

    List<Video> findByCategory(VideoCategory category);

    List<Video> findByTitleContainingIgnoreCase(String title);

    List<Video> findByDirectorContainingIgnoreCase(String director);

    List<Video> findByRatingGreaterThanEqual(Double rating);
}
