package com.dmr.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmr.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByFirstnameAndPassword(String username, String password);

	public Optional<User> findByEmailAndPassword(String email, String password);

	public Optional<User> findByEmail(String email);
}
