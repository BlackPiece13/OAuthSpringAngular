package com.dmr.service;

import com.dmr.com.dmr.exceptions.AlreadyExistsMediaException;
import com.dmr.model.Media;
import com.dmr.model.MediaType;
import com.dmr.repo.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepo;

    public List<Media> getAudiosList() {
        return mediaRepo.findFirst10ByTypeOrderByIdDesc(MediaType.AUDIO);
    }

    public List<Media> getVideosList() {
        return mediaRepo.findFirst10ByTypeOrderByIdDesc(MediaType.VIDEO);
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

    public Media add(Media media) throws AlreadyExistsMediaException {
        if (mediaRepo.findByUrlAndType(media.getUrl(), media.getType()).isPresent()) {
            throw new AlreadyExistsMediaException("Media already exists");
        }
        media.setCreationDate(LocalDateTime.now());
        return mediaRepo.save(media);
    }

    public Media update(Media media)
{
        media.setUpdateDate(LocalDateTime.now());
        return mediaRepo.save(media);
        }
        }