package com.example.demo.repositries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserForm;

@Repository
public interface UserRepository extends JpaRepository<UserForm, String>{
	Optional<UserForm> findById(String id);
	List<UserForm> findAll();
}