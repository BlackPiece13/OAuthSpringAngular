package com.dmr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmr.model.Person;
import com.dmr.model.Role;
import com.dmr.service.PersonService;

@RestController
public class AuthenticationController {

    @Autowired
    private PersonService personService;

    @GetMapping("/api/private/logout")
    public void logout(@RequestParam String access_token) {
        System.out.println("hello logout " + access_token);
        // tokenServices.revokeToken(access_token);
    }

    @RequestMapping(value = "/api/public/register", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> registerUser(@RequestBody Person user) {
        ResponseEntity<Person> resp;

        user.setRole(Role.SIMPLE_USER);

        if (!personService.add(user).isPresent()) {
            System.out.println("user already exists !!!");
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            resp = new ResponseEntity<>(user, HttpStatus.OK);
        }
        return resp;
    }
}
