package com.example.jkds.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jkds.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public Optional<User> findByUserId(String userId);
	public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
}
