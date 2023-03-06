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

import com.example.demo.models.InquiryForm;
import com.example.demo.repositries.InquiryRepository;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	InquiryRepository repository;

	@GetMapping
	public String index(Model model) {
		List<InquiryForm> inquiryList = 
			repository.findAll().stream()
				.filter(i -> i.getDeleted_at() == null)
				.collect(Collectors.toList());
		model.addAttribute("inquiryList", inquiryList);
		return "inquiry/list";
	}

	@GetMapping("/form")
	public String form(InquiryForm inquiryForm) {
		return "inquiry/form";
	}

	@PostMapping("/form")
	public String form(@Validated InquiryForm inquiryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "inquiry/form";
		}

		// RDBと連携できることを確認しておきます。
		repository.saveAndFlush(inquiryForm);
		inquiryForm.clear();
		model.addAttribute("message", "お問い合わせを受け付けました。");
		return "inquiry/form";
	}

	@GetMapping("/{id}")
	public String displayView(@PathVariable Long id, Model model) {
		InquiryForm inquiryForm = repository.findById(id).get();
    	model.addAttribute("inquiryForm", inquiryForm);
		return "inquiry/detail";
	}

	@GetMapping("/{id}/edit")
	public String displayEdit(@PathVariable Long id, Model model) {
		// もともとのデータ{id}で指定したやつ
		InquiryForm inquiryForm = repository.findById(id).get();

		// 変更後のデータ これがDBに保存される
		InquiryForm updateInquiryForm = new InquiryForm();
		updateInquiryForm.setId(inquiryForm.getId());
		updateInquiryForm.setName(inquiryForm.getName());
		updateInquiryForm.setMail(inquiryForm.getMail());
		updateInquiryForm.setContent(inquiryForm.getContent());
		updateInquiryForm.setAge(inquiryForm.getAge());
		model.addAttribute("updateInquiryForm", updateInquiryForm);
		return "inquiry/edit";
	}

	@PostMapping("/update")
	public String update(@Validated InquiryForm updateInquiryForm, BindingResult result, Model model) {
		// ユーザー情報の更新
		InquiryForm inquiry = repository.findById(updateInquiryForm.getId()).get();
		inquiry.setName(updateInquiryForm.getName());
		inquiry.setMail(updateInquiryForm.getMail());
		inquiry.setContent(updateInquiryForm.getContent());
		inquiry.setAge(updateInquiryForm.getAge());
		repository.save(inquiry);
		
		return "inquiry/update";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, Model model) {
		// {id}で指定したやつ
		InquiryForm inquiryForm = repository.findById(id).get();

		// deleted_at に現在時刻を入れて、更新する
		inquiryForm.setDeleted_at(new Date());
		repository.save(inquiryForm);

		return "inquiry/update";
	}
}