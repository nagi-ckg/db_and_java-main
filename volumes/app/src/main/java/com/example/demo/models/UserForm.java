package com.example.demo.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserForm implements Serializable {
	private static final long serialVersionUID = -6647247658748349084L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Size(max = 10)
	private String name;

	@NotBlank
	@Email
	private String mail;

	private Integer age;
	
	public void clear() {
		name = null;
		mail = null;
		age = null;
	}
}
