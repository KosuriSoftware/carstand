package com.zoro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoro.dto.RegistrationBean;
import com.zoro.service.SearchRegisteredCandidateService;

@RestController
public class SearchRegisteredCandidateController {
	
	@Autowired
	SearchRegisteredCandidateService searchRegisteredCandidateService;
	
	@GetMapping("/searchRegisteredCandidateRequests")
	public List<RegistrationBean> searchCabRequests() {
		return searchRegisteredCandidateService.searchRegisteredCandidate();
	}

}
