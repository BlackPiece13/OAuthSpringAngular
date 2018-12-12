package com.dmr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmr.model.Person;
import com.dmr.model.Role;
import com.dmr.model.User;
import com.dmr.service.PersonService;

@RestController
public class AuthenticationController {

	@Autowired
	private PersonService personService;
	@Autowired
	private BCryptPasswordEncoder bc;

	@RequestMapping(value = "/api/private/user", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> getUser(@RequestParam String login, @RequestParam String password) {
		ResponseEntity<Person> resp = new ResponseEntity<Person>(HttpStatus.NOT_FOUND);

		Optional<Person> foundPerson = personService.findByEmail(login);
		if (foundPerson.isPresent()) {
			if (bc.matches(password, foundPerson.get().getPassword())) {
				resp = new ResponseEntity<Person>(foundPerson.get(), HttpStatus.OK);
			}
		}
		return resp;
	}

	@GetMapping("/api/private/logout")
	public void logout(@RequestParam String access_token) {
		System.out.println("hello logout " + access_token);
		// tokenServices.revokeToken(access_token);
	}

	@RequestMapping(value = "/api/public/register", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> registerUser(@RequestBody User user) {
		ResponseEntity<Person> resp;
		// "User with the same email already exists.",
		Optional<Person> foundPerson = personService.findByEmail(user.getEmail());

		user.setRole(Role.USER);
		user.setPassword(bc.encode(user.getPassword()));
		if (!personService.add(user)) {
			System.out.println("user already exists");
			resp = new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
		} else {
			resp = new ResponseEntity<Person>(user, HttpStatus.OK);
		}
		return resp;
	}

}
