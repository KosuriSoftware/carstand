package com.zoro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zoro.dto.MobileVerificationBean;
import com.zoro.dto.RegistrationBean;
import com.zoro.service.MobileVerificationService;
import com.zoro.service.RegistrationService;

@RestController
public class MobileVerificationController {
	@Autowired
	MobileVerificationService mobileVerifyService;
	
	@PostMapping("/mobileVerify")
	String registerUser(@RequestBody MobileVerificationBean mobileVerifyBean){
		System.out.println(mobileVerifyBean);
		return mobileVerifyService.verifyUser(mobileVerifyBean);
	}
}
