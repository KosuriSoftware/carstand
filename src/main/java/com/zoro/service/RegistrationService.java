package com.zoro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoro.dao.RegistrationDAO;
import com.zoro.dto.RegistrationBean;

@Service
public class RegistrationService {
	
	@Autowired
	RegistrationDAO registraionDAO;
	
	public String registerUser(RegistrationBean registerBean) {
		return registraionDAO.registerUser(registerBean);
	}

}
