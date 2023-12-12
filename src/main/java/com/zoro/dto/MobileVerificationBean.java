package com.zoro.dto;

public class MobileVerificationBean {
	private String otp;
	private String mobileNumber;
	private String emailId;
	
	public MobileVerificationBean() {
		super();
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "MobileVerificationBean [otp=" + otp + ", mobileNumber=" + mobileNumber + ", emailId=" + emailId + "]";
	}
	
}
