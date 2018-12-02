package com.dmr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class MainController {
	@RequestMapping(value = "home", method = RequestMethod.GET, produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> home() {
		try {
			return new ResponseEntity<String>("hello OAuth2", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		}
	}
}
