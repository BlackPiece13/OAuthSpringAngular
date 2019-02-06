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
    public ResponseEntity<List<Media>> getTop10Audios() {
        List<Media> audiosList = mediaService.getAudiosList();
        return new ResponseEntity(audiosList, HttpStatus.OK);
    }

    @RequestMapping(value = "/top10Videos", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Media>> getTop10Videos() {
        List<Media> videosList = mediaService.getVideosList();
        return new ResponseEntity(videosList, HttpStatus.OK);
    }

    @RequestMapping(value = "/allMedias", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Media>> getAllMedias() {
        Optional<List<Media>> mediasList = mediaService.findAll();
        return new ResponseEntity(mediasList, HttpStatus.OK);
    }

    @RequestMapping(value = "/media", method = RequestMethod.POST)
    public ResponseEntity addMedia(@RequestBody Media media) {
        System.out.println("POST media : " + media);
        try {
            Media addedMedia = mediaService.add(media);
            return new ResponseEntity(addedMedia, HttpStatus.OK);
        } catch (AlreadyExistsMediaException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/media/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMedia(@PathVariable("id") Long id) {
        try {
            mediaService.deleteByID(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/media/{id}", method = RequestMethod.GET)
    public ResponseEntity getMedia(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity(mediaService.findByID(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/media", method = RequestMethod.PUT)
    public ResponseEntity updateMedia(@RequestBody Media media) {
        try {
            Media updatedMedia = mediaService.update(media);
            return new ResponseEntity(updatedMedia, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}
