package com.dmr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dmr.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dmr.model.Person;
import com.dmr.model.User;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class AuthenticationControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockmvc;
    @Autowired
    private PersonService personService;
    @Autowired
    Environment env;

    @Before
    public void beforeEach() {
        mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getUser() throws Exception {
        mockmvc.perform(get("/api/private/user?login=mail&password=123")).andExpect(status().isOk());
    }

    @Test
    public void registerUser() throws Exception {

        Optional<Person> foundPerson = personService.findByEmail("wind21@hotmail.fr");
        if (foundPerson.isPresent()) {
            personService.remove(foundPerson.get());
        }

        Person userForm = new User();
        userForm.setLogin("hamza");
        userForm.setEmail("wind21@hotmail.fr");
        userForm.setPassword("password");

        mockmvc.perform(post("/api/public/register").flashAttr("user", userForm)).andExpect(status().isBadRequest());

        //remove the added person
        personService.remove(personService.findByEmail("wind21@hotmail.fr").get());
    }

}
