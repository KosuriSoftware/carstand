package com.zoro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoro.dao.MobileVerificationDAO;
import com.zoro.dao.RegistrationDAO;
import com.zoro.dto.MobileVerificationBean;
import com.zoro.dto.RegistrationBean;

@Service
public class MobileVerificationService {
	@Autowired
	MobileVerificationDAO MobileVerificationDAO;
	
	public String verifyUser(MobileVerificationBean mobileVerifyBean) {
		System.out.println("mobService mobBean"+mobileVerifyBean);
			return MobileVerificationDAO.verifyUser(mobileVerifyBean);
	}
}
