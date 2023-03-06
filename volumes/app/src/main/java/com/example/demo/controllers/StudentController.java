package com.example.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.StudentForm;
import com.example.demo.repositries.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentRepository repository;

	@GetMapping
	public String index(Model model) {
		List<StudentForm> studentList = 
			repository.findAll().stream()
				.filter(i -> i.getDeleted_at() == null)
				.collect(Collectors.toList());
		model.addAttribute("studentList", studentList);
		return "student/list";
	}

	@GetMapping("/form")
	public String form(StudentForm studentForm) {
		return "student/form";
	}

	@PostMapping("/form")
	public String form(@Validated StudentForm studentForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "student/form";
		}

		// RDBと連携できることを確認しておきます。
		repository.saveAndFlush(studentForm);
		studentForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "student/form";
	}

	@GetMapping("/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		StudentForm studentForm = repository.findById(id).get();
    	model.addAttribute("studentForm", studentForm);
		return "student/detail";
	}

	@GetMapping("/{id}/edit")
	public String displayEdit(@PathVariable Long id, Model model) {
		// もともとのデータ{id}で指定したやつ
		StudentForm studentForm = repository.findById(id).get();

		// 変更後のデータ これがDBに保存される
		StudentForm updateStudentForm = new StudentForm();
		updateStudentForm.setId(studentForm.getId());
		updateStudentForm.setName(studentForm.getName());
		updateStudentForm.setNo(studentForm.getNo());
		updateStudentForm.setRemark(studentForm.getRemark());
		updateStudentForm.setGrade(studentForm.getGrade());
		updateStudentForm.setTeam(studentForm.getTeam());
		model.addAttribute("updateStudentForm", updateStudentForm);
		return "student/edit";
	}

	@PostMapping("/update")
	public String update(@Validated StudentForm updateInquiryForm, BindingResult result, Model model) {
		// ユーザー情報の更新
		StudentForm student = repository.findById(updateInquiryForm.getId()).get();
		student.setName(updateInquiryForm.getName());
		student.setNo(updateInquiryForm.getNo());
		student.setRemark(updateInquiryForm.getRemark());
		student.setGrade(updateInquiryForm.getGrade());
		student.setTeam(updateInquiryForm.getTeam());
		
		repository.save(student);
		
		return "student/update";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, Model model) {
		// {id}で指定したやつ
		StudentForm studentForm = repository.findById(id).get();

		// deleted_at に現在時刻を入れて、更新する
		studentForm.setDeleted_at(new Date());
		repository.save(studentForm);

		return "student/update";
	}
}