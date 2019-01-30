package com.dmr;

import com.dmr.model.Gender;
import com.dmr.model.User;
import com.dmr.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addPerson() {
        User person = new User();
        person.setEmail("mail@mail.fr");
        person.setFirstname("hamza");
        person.setGender(Gender.MALE);
        person.setLogin("login");
        person.setPassword("password");
        try {
            userService.add(person);
        } catch (Exception e) {
        }
        Optional<User> foundPerson = userService.findByEmail(person.getEmail());
        assertTrue(foundPerson.isPresent());
        userService.delete(person);
    }

    @Test
    public void removePerson() {
        User person = new User();
        person.setEmail("mailBis@mail.fr");
        person.setFirstname("hamza");
        person.setGender(Gender.MALE);
        person.setLogin("login");
        person.setPassword("password");
        try {
            userService.add(person);
        } catch (Exception e) {

        }
        Optional<User> foundPerson = userService.findByEmail("mailBis@mail.fr");
        assertTrue(foundPerson.isPresent());
        userService.delete(person);
        foundPerson = userService.findByEmail("mailBis@mail.fr");
        assertTrue(!foundPerson.isPresent());
    }

    @Test
    public void testIfPersonExists() {
        User person = new User();
        person.setEmail("mailBis@mail.fr");
        person.setFirstname("hamza");
        person.setGender(Gender.MALE);
        person.setLogin("login");
        person.setPassword("password");
        try {
            userService.add(person);
        } catch (Exception e) {
        }
        assertTrue(userService.exists(person.getEmail()));
        userService.delete(person);
        assertFalse(userService.exists(person.getEmail()));
    }

    @Test
    public void findAll() {
        // j'ai deja enregistre trois utilisateurs
        List<User> personsList = userService.findAll();
        assertTrue(personsList.size() == 3);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setFirstname("aouadene");
        user.setLastname("hamza");
        user.setEmail("atom@mail.fr");
        user.setPassword("password");
        Optional<User> addedUser = userService.add(user);

        user.setFirstname("haddadi");
        userService.update(user);

        Optional<User> updatedUser = userService.findByEmail("atom@mail.fr");
        assertEquals(updatedUser.get().getFirstname(), "haddadi");

        userService.delete(addedUser.get());
    }


}
