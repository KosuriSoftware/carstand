package com.zoro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoro.dto.RegistrationBean;
import com.zoro.service.RegistrationService;

@RestController
public class RegistrationController {
	
	@Autowired
	RegistrationService registrationService;
	
	@PostMapping("/registration")
	String registerUser(@RequestBody RegistrationBean registerBean){
		System.out.println(registerBean);
		return registrationService.registerUser(registerBean);
	}

}
