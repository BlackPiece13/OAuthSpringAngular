package com.dmr.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

//	@Resource(name = "defaultTokenServices")
//	ConsumerTokenServices tokenServices;

	@RequestMapping(value = "home", method = RequestMethod.GET, produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> home() {
		try {
			return new ResponseEntity<String>("hello OAuth2", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@RequestParam String login, @RequestParam String password) {
		ResponseEntity<User> resp = new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		List<User> users = userRepo.findAll();

		for (User user : users) {
			if (user.getEmail().equals(login) && bc.matches(password, user.getPassword())) {
				resp = new ResponseEntity<User>(user, HttpStatus.OK);
				break;
			}
		}
		return resp;
	}

	@GetMapping("logout")
	public void logout(@RequestParam String access_token) {
		System.out.println("hello logout "+access_token);
		//tokenServices.revokeToken(access_token);
	}

}
