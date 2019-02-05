package com.dmr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dmr.com.dmr.exceptions.UserAlreadyExistsException;
import com.dmr.dto.UserDTO;
import com.dmr.model.User;
import com.dmr.service.UserService;
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
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@RequestParam String email) {
        Optional<User> foundPerson = userService.findByEmail(email);
        if (!foundPerson.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = userService.getUserDTO(foundPerson.get());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByID(@PathVariable("id") Long id) {
        Optional<User> foundPerson = userService.findByID(id);
        if (!foundPerson.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = userService.getUserDTO(foundPerson.get());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allPersons = userService.findAll();

        List<UserDTO> usersDTOList = new ArrayList<>();
        for (User person : allPersons) {
            usersDTOList.add(userService.getUserDTO(person));
        }
        return new ResponseEntity<>(usersDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        userService.deleteById(Long.parseLong(id));
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        return response;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        if (!userService.update(user).isPresent()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(userService.getUserDTO(user), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        try {
            UserDTO userDTO = userService.getUserDTO(userService.add(user).get());
            return new ResponseEntity(userDTO, HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}