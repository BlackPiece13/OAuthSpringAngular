package com.dmr.service;

import com.dmr.model.Media;
import com.dmr.model.MediaType;
import com.dmr.repo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepo;

    public List<Media> getAudiosList() {
        return mediaRepo.findFirst10ByTypeOrderByIdDesc(MediaType.AUDIO);
    }

    public Optional<Media> findByID(Long id) {
        return mediaRepo.findById(id);
    }

    public Optional<List<Media>> findAll() {
        return Optional.ofNullable(mediaRepo.findAll());
    }

    public void deleteByID(Long id) {
        mediaRepo.deleteById(id);
    }

    public void delete(Media media) {
        mediaRepo.delete(media);
    }

    public void add(Media media) {
        mediaRepo.save(media);
    }

    public void update(Media media) {
        mediaRepo.save(media);
    }
}
