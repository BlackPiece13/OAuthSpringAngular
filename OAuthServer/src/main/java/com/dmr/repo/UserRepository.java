package com.dmr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dmr.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByFirstname(String firstName);

	public User findByFirstnameAndPassword(String username, String password);

	public User findByEmailAndPassword(String email, String password);

	public User findByEmail(String email);
}
