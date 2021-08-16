package com.mock.project.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mock.project.service.EmailService;

@Controller
public class EmailController {
	@Autowired
	EmailService emailService;
	
	@PostMapping("/sendEmail")
	public String sendNotifyEmail(RedirectAttributes redirect, HttpServletRequest request) throws MessagingException, IOException {
		emailService.sendMail("sadasd", "ádasdasd", "ádasds");
		System.out.println("ádasdasdas");
		return "redirect:/";
	}

}
