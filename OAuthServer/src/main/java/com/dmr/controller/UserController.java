package com.dmr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dmr.dto.UserDTO;
import com.dmr.model.Person;
import com.dmr.service.PersonService;
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
        ResponseEntity<UserDTO> response = new ResponseEntity<>(userDTO, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List <Person> allPersons = personService.findAll();

        List <UserDTO> usersDTOList = new ArrayList<>();
        for(Person person : allPersons) {
            usersDTOList.add(personService.getUserDTO(person));
        }

        ResponseEntity<List<UserDTO>> response = new ResponseEntity<>(usersDTOList, HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@PathVariable String id) {
        personService.deleteById(Long.getLong(id));
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody Person user) {

        if (user.getEmail() == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<UserDTO> resp;
        Optional<Person> foundUser = personService.findByEmail(user.getEmail());
        if (!foundUser.isPresent()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setPassword(foundUser.get().getPassword());
        user.setId(foundUser.get().getId());

        if (!personService.update(user).isPresent()) {
            resp = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            resp = new ResponseEntity<>(personService.getUserDTO(user), HttpStatus.OK);
        }
        return resp;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@RequestBody Person user) {
        ResponseEntity<UserDTO> resp;
        Optional<Person> foundUser = personService.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDTO userDTO = personService.getUserDTO(personService.add(user).get());
        resp = new ResponseEntity(userDTO, HttpStatus.OK);
        return resp;
    }

}