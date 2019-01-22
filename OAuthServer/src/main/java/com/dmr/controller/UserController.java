package com.dmr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dmr.dto.UserDTO;
import com.dmr.model.Person;
import com.dmr.service.PersonService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private")
public class UserController {

    @Autowired
    private PersonService personService;

/*
@Resource(name = "defaultTokenServices")
ConsumerTokenServices tokenServices;
*/

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@RequestParam String email) {
        Optional<Person> foundPerson = personService.findByEmail(email);
        if (!foundPerson.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = personService.getUserDTO(foundPerson.get());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByID(@PathVariable("id") Long id) {
        Optional<Person> foundPerson = personService.findByID(id);
        if (!foundPerson.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = personService.getUserDTO(foundPerson.get());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<Person> allPersons = personService.findAll();

        List<UserDTO> usersDTOList = new ArrayList<>();
        for (Person person : allPersons) {
            usersDTOList.add(personService.getUserDTO(person));
        }
        return new ResponseEntity<>(usersDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        personService.deleteById(Long.parseLong(id));
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody Person user) {
        if (!personService.update(user).isPresent()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(personService.getUserDTO(user), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@RequestBody Person user) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<UserDTO> resp;
        Optional<Person> foundUser = personService.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDTO userDTO = personService.getUserDTO(personService.add(user).get());
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }
}