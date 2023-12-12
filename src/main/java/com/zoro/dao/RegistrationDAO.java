package com.zoro.dao;

import com.zoro.dto.RegistrationBean;


public interface RegistrationDAO {
	 String registerUser(RegistrationBean registerBean);
	 String regEmailCheck(RegistrationBean registerBean);
	 String regMobilecheck(RegistrationBean registerBean);
	 String regMobileSendSMS(RegistrationBean registerBean);
	 String regEmailSendSMS(RegistrationBean registerBean);
}
