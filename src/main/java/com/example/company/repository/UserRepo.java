package com.example.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.company.entity.Users;

@Repository
public interface UserRepo extends JpaRepository <Users, Long> {


	Optional<Users> findByEmail(String email);
	
}
