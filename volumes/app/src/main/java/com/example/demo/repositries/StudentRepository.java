package com.example.demo.repositries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.StudentForm;

@Repository
public interface StudentRepository extends JpaRepository<StudentForm, String>{
	Optional<StudentForm> findById(Long id);
	List<StudentForm> findAll();
}