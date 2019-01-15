package com.dmr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.dmr.model.SimpleUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dmr.model.Gender;
import com.dmr.model.Person;
import com.dmr.model.Role;
import com.dmr.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void test() {
        Person person = new SimpleUser();
        assertTrue("User with wrong role", person.getRole().compareTo(Role.SIMPLE_USER) == 0);
    }

    @Test
    public void addPerson() {
        Person person = new SimpleUser();
        person.setEmail("mail@mail.fr");
        person.setFirstname("hamza");
        person.setGender(Gender.MALE);
        person.setLogin("login");
        person.setPassword("password");
        personService.add(person);
        Optional<Person> foundPerson = personService.findByEmail(person.getEmail());
        assertTrue(foundPerson.isPresent());
        personService.delete(person);
    }

    @Test
    public void removePerson() {
        Person person = new SimpleUser();
        person.setEmail("mailBis@mail.fr");
        person.setFirstname("hamza");
        person.setGender(Gender.MALE);
        person.setLogin("login");
        person.setPassword("password");
        personService.add(person);
        Optional<Person> foundPerson = personService.findByEmail("mailBis@mail.fr");
        assertTrue(foundPerson.isPresent());
        personService.delete(person);
        foundPerson = personService.findByEmail("mailBis@mail.fr");
        assertTrue(!foundPerson.isPresent());
    }

    @Test
    public void testIfPersonExists() {
        Person person = new SimpleUser();
        person.setEmail("mailBis@mail.fr");
        person.setFirstname("hamza");
        person.setGender(Gender.MALE);
        person.setLogin("login");
        person.setPassword("password");
        personService.add(person);

        assertTrue(personService.exists(person.getEmail()));

        personService.delete(person);


        assertFalse(personService.exists(person.getEmail()));
    }

}