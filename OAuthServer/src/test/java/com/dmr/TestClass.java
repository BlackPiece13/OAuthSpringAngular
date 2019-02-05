package com.dmr;

import com.dmr.model.Media;
import com.dmr.model.MediaType;
import com.dmr.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestClass {
    @Mock
    private User user;// = new User();

    @Spy
    private Media media;//= new Media();

    @Before
    public void init() {
        //media.setTitle("titre");
        Mockito.when(user.getEmail()).thenReturn("hamza");
        Mockito.when(media.getType()).thenReturn(MediaType.VIDEO);
        //media.setType(MediaType.VIDEO);
    }

    @Test
    public void testB() {
        //Mockito.when(user.getEmail()).thenReturn("hamza");
        assert (user.getEmail().equals("hamza"));
        assert (media.getType().equals(MediaType.VIDEO));
    }

    @Test
    public void test() {
        assert (user.getEmail().equals("hamza"));
    }
}
