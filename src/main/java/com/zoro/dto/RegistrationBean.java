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
	
	public RegistrationBean() {
		super();
	}
	
	
	public RegistrationBean(int regId, String candidateName, String address, String emailId, String password,
			String contactNo, String userType, String status, String registerDate, String candidateId,
			String businessName) {
		super();
		this.regId = regId;
		this.candidateName = candidateName;
		this.address = address;
		this.emailId = emailId;
		this.password = password;
		this.contactNo = contactNo;
		this.userType = userType;
		this.status = status;
		this.registerDate = registerDate;
		this.candidateId = candidateId;
		this.businessName = businessName;
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


	@Override
	public String toString() {
		return "RegistrationBean [regId=" + regId + ", candidateName=" + candidateName + ", address=" + address
				+ ", emailId=" + emailId + ", password=" + password + ", contactNo=" + contactNo + ", userType="
				+ userType + ", status=" + status + ", registerDate=" + registerDate + ", candidateId=" + candidateId
				+ ", businessName=" + businessName + "]";
	}
	
	
	
}
