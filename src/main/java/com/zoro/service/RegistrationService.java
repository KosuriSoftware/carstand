package com.zoro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zoro.dao.RegistrationDAO;
import com.zoro.dto.RegistrationBean;
import com.zoro.sms.utilities.OtpGenerator;

@Service
public class RegistrationService {
	
	@Autowired
	RegistrationDAO registraionDAO;
	
	public String registerUser(RegistrationBean registerBean) {
		String msg="";
		System.out.println("RegistrationService registerBean"+registerBean);
		//check for mobile & email status
		String status=registraionDAO.regEmailCheck(registerBean);
		String mobstatus = registraionDAO.regMobilecheck(registerBean);
		//otp gen
		OtpGenerator otpgen1 = new OtpGenerator();
		char[] otpgen = otpgen1.OTP(6);
		String otps=String.valueOf(otpgen);
		registerBean.setStatus(otps);
		
		if(status==null && mobstatus==null){
			return registraionDAO.registerUser(registerBean);
		}else if(status!=null && status.equals("INACTIVE")){
			//new VerificationMail().verifyMail(reg.getEMAIL_ID()); ...siva to write code
			msg="inactive";
			
		}else if(status!=null && status.equals("ACTIVE")){
			msg="active";
		}
		else if(mobstatus!=null && mobstatus.equals("ACTIVE")){
			msg="mobile";
		}
		else if(mobstatus!=null && mobstatus.equals("INACTIVE")){
			msg="mobile";
		}
		/*mailing and sms code*/
		if(status==null && !msg.equals("") && mobstatus==null){
			registerBean.setGenOTP(otps);
			registraionDAO.regMobileSendSMS(registerBean);
			registraionDAO.regEmailSendSMS(registerBean);
			
		}
		return "Bad request";
	}

}
