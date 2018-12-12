package com.dmr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmr.model.Person;
import com.dmr.repo.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepo;

	public Optional<Person> findByFirstnameAndPassword(String username, String password) {
		return personRepo.findByFirstnameAndPassword(username, password);
	}

	public Optional<Person> findByEmailAndPassword(String email, String password) {
		return personRepo.findByEmailAndPassword(email, password);
	}

	public Optional<Person> findByEmail(String email) {
		return personRepo.findByEmail(email);
	}

	public boolean add(Person person) {
		if (exists(person.getEmail())) {
			return false;
		}
		personRepo.save(person);
		return true;
	}

	public boolean update(Person person) {
		if (exists(person.getId()) || exists(person.getEmail())) {
			personRepo.save(person);
			return true;
		}
		return false;
	}

	public void remove(Person person) {
		personRepo.delete(person);
	}

	public boolean exists(Long id) {
		Optional<Person> foundPseron = personRepo.findById(id);
		return foundPseron.isPresent();
	}

	public boolean exists(String email) {
		Optional<Person> foundPerson = personRepo.findByEmail(email);
		return foundPerson.isPresent();
	}

}
