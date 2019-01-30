package com.dmr.service;

import java.util.List;
import java.util.Optional;

import com.dmr.com.dmr.exceptions.UserAlreadyExistsException;
import com.dmr.dto.UserDTO;
import com.dmr.model.User;
import com.dmr.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bc;
    @Autowired
    private UserRepository userRepo;


    public Optional<User> findByFirstnameAndPassword(String username, String password) {
        return userRepo.findByFirstnameAndPassword(username, password);
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {

        Optional<User> foundPerson = findByEmail(email);
        if (foundPerson.isPresent()) {
            if (bc.matches(password, foundPerson.get().getPassword())) {
                return foundPerson;
            }
        }
        return Optional.ofNullable(null);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findByID(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> add(User user) throws UserAlreadyExistsException {

        Optional<User> foundPerson = findByEmail(user.getEmail());
        if (foundPerson.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setPassword(bc.encode(user.getPassword()));
        return Optional.ofNullable(userRepo.save(user));

    }

    public Optional<User> update(User user) {
        Optional<User> entityToUpdate = userRepo.findById(user.getId());

        entityToUpdate.get().setFirstname(user.getFirstname());
        entityToUpdate.get().setLastname(user.getLastname());
        entityToUpdate.get().setLogin(user.getLogin());
        entityToUpdate.get().setEmail(user.getEmail());
        entityToUpdate.get().setGender(user.getGender());
        entityToUpdate.get().setRole(user.getRole());

        return Optional.ofNullable(userRepo.save(entityToUpdate.get()));
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public boolean exists(String email) {
        Optional<User> foundPerson = userRepo.findByEmail(email);
        return foundPerson.isPresent();
    }

    public UserDTO getUserDTO(User user) {
        return UserDTO.builder().id(user.getId()).firstname(user.getFirstname()).lastname(user.getLastname()).
                role(user.getRole()).login(user.getLogin()).gender(user.getGender()).email(user.getEmail()).build();
    }
}