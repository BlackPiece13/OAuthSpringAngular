package com.dmr.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmr.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByFirstnameAndPassword(String username, String password);

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByEmail(String email);

}
