package com.dmr.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmr.model.User;
import com.dmr.repo.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder bc;

	/* for testing */
	@PostConstruct
	public void createNewUsers() {
		User user1 = new User();
		user1.setFirstname("user1");
		user1.setEmail("mail");
		user1.setPassword(bc.encode("123"));
		userRepo.save(user1);

		User user2 = new User();
		user2.setFirstname("user2");
		user2.setEmail("mail1");
		user2.setPassword(bc.encode("456"));
		userRepo.save(user2);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("invalid user name");
		}
		return new org.springframework.security.core.userdetails.User(user.getFirstname(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
	}
}
