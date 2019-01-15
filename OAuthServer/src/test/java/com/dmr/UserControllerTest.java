package com.dmr;

import com.dmr.model.Person;
import com.dmr.model.SimpleUser;
import com.dmr.service.PersonService;
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
    private PersonService personService;
    @Autowired
    Environment env;

    @Before
    public void beforeEach() {
        mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getUser() throws Exception {
        Person user = new SimpleUser();
        user.setLogin("hamza");
        user.setEmail("mail30@mail.fr");
        user.setPassword("password");
        user = personService.add(user).get();
        mockmvc.perform(get("/api/private/user?login=mail30@mail.fr&password=password")).andExpect(status().isOk());
        personService.delete(user);
    }

    @Test
    public void addUser() throws Exception {
        System.out.println("Add User Test        /////////////////////");
        Optional<Person> foundPerson = personService.findByEmail("wind21@hotmail.fr");
        if (foundPerson.isPresent()) {
            personService.deleteById(foundPerson.get().getId());
        }

        Person userForm = new SimpleUser();
        userForm.setLogin("hamza");
        userForm.setEmail("Mail50@mail.fr");
        userForm.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();

        mockmvc.perform(post("/api/private/addUser").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userForm)))
                .andExpect(status().isOk());

        Optional<Person> foundAddedPerson = personService.findByEmail(userForm.getEmail());
        assertTrue(foundAddedPerson.isPresent());

        //remove the added person
        personService.deleteById(personService.findByEmail("Mail50@mail.fr").get().getId());
    }
}







