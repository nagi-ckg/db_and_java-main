package com.example.demo.models;

import java.util.Date;

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
@Table(name = "student")
public class StudentForm implements Serializable {
	private static final long serialVersionUID = -6647247658748349084L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Size(max = 40)
	private String name;

	@NotBlank
	@Size(max = 5)
	private String no;

	@Size(max = 400)
	private String remark;

	private Integer grade;
	private String team;
	private Date deleted_at; 
	
	public void clear() {
		name = null;
		no = null;
		remark = null;
		grade = null;
		team = null;
		deleted_at = null;
	}
}
