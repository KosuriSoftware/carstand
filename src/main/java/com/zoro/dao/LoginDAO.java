package com.zoro.dao;

import java.util.Map;


public interface LoginDAO {
	public Map<String, String> loginCheck(String emailId, String mobileNo);
	public String updateMobileOtp(String otp,String mobileNo);
	public Map<String,String> getMobileVerificationDetails(String mobileNo);
}
