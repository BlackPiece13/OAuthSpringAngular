package com.dmr.service;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.dmr.model.Role;
import com.dmr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("springAuthService")
public class SpringAuthService implements UserDetailsService {
    @Autowired
    private UserService userService;

    /* for testing */
    @PostConstruct
    public void createNewUsers() {
        User user1 = new User();
        user1.setRole(Role.SIMPLE_USER);
        user1.setFirstname("user1");
        user1.setEmail("mail");
        user1.setPassword("123");
        userService.add(user1);

        User user2 = new User();
        user2.setRole(Role.SIMPLE_USER);
        user2.setFirstname("user2");
        user2.setEmail("mail1");
        user2.setPassword("456");
        userService.add(user2);

        User admin = new User();
        admin.setRole(Role.ADMIN);
        admin.setFirstname("admin");
        admin.setEmail("mail3@mail.fr");
        admin.setPassword("123");
        userService.add(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("invalid user name");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
