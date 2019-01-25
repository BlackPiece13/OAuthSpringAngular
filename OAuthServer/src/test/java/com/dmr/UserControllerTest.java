package com.dmr;

import com.dmr.model.User;
import com.dmr.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockmvc;
    @Autowired
    private UserService userService;
    @Autowired
    Environment env;

    @Before
    public void beforeEach() {
        mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getUser() throws Exception {
        User user = new User();
        user.setLogin("hamza");
        user.setEmail("mail30@mail.fr");
        user.setPassword("password");
        user = userService.add(user).get();
        mockmvc.perform(get("/api/private/user?email=mail30@mail.fr")).andExpect(status().isOk());
        userService.delete(user);
    }

    @Test
    public void addUser() throws Exception {
        Optional<User> foundPerson = userService.findByEmail("wind21@hotmail.fr");
        if (foundPerson.isPresent()) {
            userService.deleteById(foundPerson.get().getId());
        }

        User userForm = new User();
        userForm.setLogin("hamza");
        userForm.setEmail("Mail50@mail.fr");
        userForm.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        mockmvc.perform(post("/api/private/addUser").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userForm)))
                .andExpect(status().isOk());

        Optional<User> foundAddedPerson = userService.findByEmail(userForm.getEmail());
        assertTrue(foundAddedPerson.isPresent());

        //remove the added person
        userService.deleteById(userService.findByEmail("Mail50@mail.fr").get().getId());
    }
}







