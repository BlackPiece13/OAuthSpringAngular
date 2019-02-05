package com.dmr.controller;

import com.dmr.com.dmr.exceptions.AlreadyExistsMediaException;
import com.dmr.model.Media;
import com.dmr.model.User;
import com.dmr.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @RequestMapping(value = "/top10Audios", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Media>> getTop10Medias() {
        List<Media> mediasList = mediaService.getAudiosList();
        return new ResponseEntity(mediasList, HttpStatus.OK);
    }

    @RequestMapping(value = "/allMedias", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Media>> getAllMedias() {
        Optional<List<Media>> mediasList = mediaService.findAll();
        return new ResponseEntity(mediasList, HttpStatus.OK);
    }

    @RequestMapping(value = "/media", method = RequestMethod.POST)
    public ResponseEntity addMedia(@RequestBody Media media) {
        try {
            mediaService.add(media);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AlreadyExistsMediaException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/media/{id}", method = RequestMethod.GET)
    public ResponseEntity deleteMedia(@PathVariable("id") Long id) {
        try {
            mediaService.deleteByID(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/media", method = RequestMethod.PUT)
    public ResponseEntity updateMedia(@RequestBody Media media) {
        try {
            mediaService.update(media);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
