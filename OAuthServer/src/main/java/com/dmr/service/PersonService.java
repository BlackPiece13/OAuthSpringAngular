package com.dmr.service;

import java.util.List;
import java.util.Optional;

import com.dmr.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmr.model.Person;
import com.dmr.repo.PersonRepository;

@Service
public class PersonService {
    @Autowired
    private BCryptPasswordEncoder bc;
    @Autowired
    private PersonRepository personRepo;


    public Optional<Person> findByFirstnameAndPassword(String username, String password) {
        return personRepo.findByFirstnameAndPassword(username, password);
    }

    public Optional<Person> findByEmailAndPassword(String email, String password) {

        Optional<Person> foundPerson = findByEmail(email);
        if (foundPerson.isPresent()) {
            if (bc.matches(password, foundPerson.get().getPassword())) {
                return foundPerson;
            }
        }
        return Optional.ofNullable(null);
    }

    public List<Person> findAll() {
        return personRepo.findAll();
    }

    public Optional<Person> findByEmail(String email) {
        return personRepo.findByEmail(email);
    }

    public Optional<Person> add(Person person) {

        Optional<Person> foundPerson = findByEmail(person.getEmail());
        if (foundPerson.isPresent()) {
            return Optional.ofNullable(null);
        }
        person.setPassword(bc.encode(person.getPassword()));
        return Optional.ofNullable(personRepo.save(person));

    }

    public Optional<Person> update(Person person) {
        return Optional.ofNullable(personRepo.save(person));
    }

    public void delete(Person person) {
        personRepo.delete(person);
    }

    public void deleteById(Long id) {
        personRepo.deleteById(id);
    }

    public boolean exists(Long id) {
        Optional<Person> foundPerson = personRepo.findById(id);
        return foundPerson.isPresent();
    }

    public boolean exists(String email) {
        Optional<Person> foundPerson = personRepo.findByEmail(email);
        return foundPerson.isPresent();
    }

    public UserDTO getUserDTO(Person person) {
        return UserDTO.builder().id(person.getId()).firstname(person.getFirstname()).
                lastname(person.getLastname()).email(person.getEmail()).gender(person.getGender()).
                login(person.getLogin()).role(person.getRole()).build();
    }
}