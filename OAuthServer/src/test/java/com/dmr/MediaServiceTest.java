package com.dmr;


import com.dmr.com.dmr.exceptions.AlreadyExistsMediaException;
import com.dmr.model.Media;
import com.dmr.model.MediaType;
import com.dmr.service.MediaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class MediaServiceTest {
    @Autowired
    private MediaService mediaService;

    @Test
    public void addAndDeleteMedia() {
        assertTrue(mediaService.findAll().get().size() == 0);
        Media media = new Media();
        media.setTitle("article de presse");
        media.setType(MediaType.AUDIO);
        try {
            mediaService.add(media);
            assertTrue(mediaService.findAll().get().size() == 1);
            mediaService.delete(media);
            assertTrue(mediaService.findAll().get().size() == 0);
        } catch (AlreadyExistsMediaException e) {
            e.printStackTrace();
        }
    }
}
