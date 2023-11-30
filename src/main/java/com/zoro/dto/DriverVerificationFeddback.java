package com.zoro.dto;

public class DriverVerificationFeddback {
	
	private int DR_FD_SEQ_NO;
	private String DRIVER_EMAIL;
	private String FEED_BACK;
	private String ADMIN_EMAIL;
	private String DRIVER_ID;
	private String FEED_BACK_DATE;
	
	public int getDR_FD_SEQ_NO() {
		return DR_FD_SEQ_NO;
	}
	public void setDR_FD_SEQ_NO(int dR_FD_SEQ_NO) {
		DR_FD_SEQ_NO = dR_FD_SEQ_NO;
	}
	public String getDRIVER_EMAIL() {
		return DRIVER_EMAIL;
	}
	public void setDRIVER_EMAIL(String dRIVER_EMAIL) {
		DRIVER_EMAIL = dRIVER_EMAIL;
	}
	public String getFEED_BACK() {
		return FEED_BACK;
	}
	public void setFEED_BACK(String fEED_BACK) {
		FEED_BACK = fEED_BACK;
	}
	public String getADMIN_EMAIL() {
		return ADMIN_EMAIL;
	}
	public void setADMIN_EMAIL(String aDMIN_EMAIL) {
		ADMIN_EMAIL = aDMIN_EMAIL;
	}
	public String getDRIVER_ID() {
		return DRIVER_ID;
	}
	public void setDRIVER_ID(String dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}
	public String getFEED_BACK_DATE() {
		return FEED_BACK_DATE;
	}
	public void setFEED_BACK_DATE(String fEED_BACK_DATE) {
		FEED_BACK_DATE = fEED_BACK_DATE;
	}
	
	

}
