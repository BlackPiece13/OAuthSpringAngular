package com.dmr.service;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmr.model.Person;
import com.dmr.model.User;

@Service("userService")
public class UserService implements UserDetailsService {
	@Autowired
	private PersonService personService;
	@Autowired
	private BCryptPasswordEncoder bc;

	/* for testing */
	@PostConstruct
	public void createNewUsers() {
		User user1 = new User();
		user1.setFirstname("user1");
		user1.setEmail("mail");
		user1.setPassword(bc.encode("123"));
		personService.add(user1);

		User user2 = new User();
		user2.setFirstname("user2");
		user2.setEmail("mail1");
		user2.setPassword(bc.encode("456"));
		personService.add(user2);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> user = personService.findByEmail(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("invalid user name");
		}
		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}
}
