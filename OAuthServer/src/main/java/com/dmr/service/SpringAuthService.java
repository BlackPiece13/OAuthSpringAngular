package com.dmr.service;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.dmr.model.Admin;
import com.dmr.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmr.model.Person;

@Service("springAuthService")
public class SpringAuthService implements UserDetailsService {
    @Autowired
    private PersonService personService;

    /* for testing */
    @PostConstruct
    public void createNewUsers() {
        SimpleUser user1 = new SimpleUser();
        user1.setFirstname("user1");
        user1.setEmail("mail");
        user1.setPassword("123");
        personService.add(user1);

        SimpleUser user2 = new SimpleUser();
        user2.setFirstname("user2");
        user2.setEmail("mail1");
        user2.setPassword("456");
        personService.add(user2);

        Person admin = new Admin();
        admin.setFirstname("admin");
        admin.setEmail("mail3@mail.fr");
        admin.setPassword("123");
        personService.add(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = personService.findByEmail(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("invalid user name");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
