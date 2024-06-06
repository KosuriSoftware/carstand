package com.zoro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zoro.dto.LoginRequest;
import com.zoro.dto.LoginResponse;
import com.zoro.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		String token = loginService.login(loginRequest);
		System.out.println("Test Github...............");
		return ResponseEntity.ok(new LoginResponse(token));
		
	}

}
