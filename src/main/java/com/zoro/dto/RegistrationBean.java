package com.zoro.dto;

public class RegistrationBean {
	
	private int regId;
	private String candidateName;
	private String address;
	private String emailId;
	private String password;
	private String contactNo;
	private String userType;
	private String status;
	private String registerDate;
	private String candidateId;
	private String businessName;
	private String mobileOTPMesg;
	private String emailOTPMsg;
	private String genOTP;
	public RegistrationBean() {
		super();
	}
	public int getRegId() {
		return regId;
	}
	public void setRegId(int regId) {
		this.regId = regId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getMobileOTPMesg() {
		return mobileOTPMesg;
	}
	public void setMobileOTPMesg(String mobileOTPMesg) {
		this.mobileOTPMesg = mobileOTPMesg;
	}
	public String getEmailOTPMsg() {
		return emailOTPMsg;
	}
	public void setEmailOTPMsg(String emailOTPMsg) {
		this.emailOTPMsg = emailOTPMsg;
	}
	public String getGenOTP() {
		return genOTP;
	}
	public void setGenOTP(String genOTP) {
		this.genOTP = genOTP;
	}
	@Override
	public String toString() {
		return "RegistrationBean [regId=" + regId + ", candidateName=" + candidateName + ", address=" + address
				+ ", emailId=" + emailId + ", password=" + password + ", contactNo=" + contactNo + ", userType="
				+ userType + ", status=" + status + ", registerDate=" + registerDate + ", candidateId=" + candidateId
				+ ", businessName=" + businessName + ", mobileOTPMesg=" + mobileOTPMesg + ", emailOTPMsg=" + emailOTPMsg
				+ ", genOTP=" + genOTP + "]";
	}

	
	
	
}
