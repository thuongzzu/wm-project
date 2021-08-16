package com.mock.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mock.project.service.EmailService;
import com.mock.project.service.TeacherService;

@Controller
public class MainController {
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/")
	public String index() {		
		return "index";
	}
	
	@GetMapping("/admin") 
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}
	
	@GetMapping("/login") 
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/main-page")
	public String loginIndex() {
		return "loginIndex";
	}
	
	@GetMapping("/chem")
	public String chem() {
		return "chem";
	}
	
}
