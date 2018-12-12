package com.dmr.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmr.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	public Optional<Person> findByFirstname(String firstName);

	public Optional<Person> findByFirstnameAndPassword(String username, String password);

	public Optional<Person> findByEmailAndPassword(String email, String password);

	public Optional<Person> findByEmail(String email);
}
