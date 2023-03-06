package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.models.UserForm;
import com.example.demo.repositries.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository repository;

	@GetMapping
	public String index(UserForm userForm) {
		return "user/index";
	}

	@PostMapping
	public String index(@Validated UserForm userForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "user/index";
		}

		// RDBと連携できることを確認しておきます。
		repository.saveAndFlush(userForm);
		userForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "user/index";
	}
}