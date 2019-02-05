package com.dmr.repo;

import com.dmr.model.Media;
import com.dmr.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Media> findFirst10ByTypeOrderByIdDesc(MediaType type);

    Optional<Media> findByUrlAndType(String url, MediaType mediaType);
}
