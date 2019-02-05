package com.dmr.controller;

import com.dmr.com.dmr.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmr.model.User;
import com.dmr.model.Role;
import com.dmr.service.UserService;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultTokenServices tokenServices;

    @GetMapping("/api/public/logout")
    public ResponseEntity logout(@RequestParam String token) {
        try {
            tokenServices.revokeToken(token);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/public/register", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        ResponseEntity<User> resp;
        user.setRole(Role.SIMPLE_USER);
        try {
            userService.add(user);
            System.out.println("user already exists !!!");
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}