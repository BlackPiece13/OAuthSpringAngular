package com.dmr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmr.model.User;
import com.dmr.repo.UserRepository;

@RestController
@RequestMapping("/api/private")
public class MainController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder bc;

	@RequestMapping(value = "home", method = RequestMethod.GET, produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> home() {
		try {
			return new ResponseEntity<String>("hello OAuth2", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.GET, produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
	public ResponseEntity<User> getUser(@RequestParam String login, @RequestParam String password) {
		ResponseEntity<User> resp = new ResponseEntity<User>(HttpStatus.OK);

		System.out.println("login " + login + " password " + password);
		List<User> users = userRepo.findAll();

		// System.out.println("Hash : " + bc.encode(password) + " user " + user);

		for (User user : users) {
			if (user.getEmail().equals(login) && bc.matches(password, user.getPassword())) {
				System.out.println(user.getEmail() + "  //  " + user.getFirstname());
				break;
			}
		}

//		resp.getBody().setId(user.getId());
//		resp.getBody().setFirstname(user.getFirstname());
//		resp.getBody().setLastname(user.getLastname());
//		resp.getBody().setGender(user.getGender());

		return resp;
	}
}
