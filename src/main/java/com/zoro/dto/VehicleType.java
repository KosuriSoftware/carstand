package com.zoro.dto;

public class VehicleType {

	private int VEHICLE_SEQ_NO;
	private String BRAND;
	private String MODEL;
	private String IMAGE;
	private String ADMIN_ID;
	private String NO_OF_PASSENGER;
	private String BODY_TYPE;
	
	public int getVEHICLE_SEQ_NO() {
		return VEHICLE_SEQ_NO;
	}
	public void setVEHICLE_SEQ_NO(int vEHICLE_SEQ_NO) {
		VEHICLE_SEQ_NO = vEHICLE_SEQ_NO;
	}
	public String getIMAGE() {
		return IMAGE;
	}
	public void setIMAGE(String iMAGE) {
		IMAGE = iMAGE;
	}
	public String getADMIN_ID() {
		return ADMIN_ID;
	}
	public void setADMIN_ID(String aDMIN_ID) {
		ADMIN_ID = aDMIN_ID;
	}
	public String getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}
	public String getMODEL() {
		return MODEL;
	}
	public void setMODEL(String mODEL) {
		MODEL = mODEL;
	}
	public String getNO_OF_PASSENGER() {
		return NO_OF_PASSENGER;
	}
	public void setNO_OF_PASSENGER(String nO_OF_PASSENGER) {
		NO_OF_PASSENGER = nO_OF_PASSENGER;
	}
	public String getBODY_TYPE() {
		return BODY_TYPE;
	}
	public void setBODY_TYPE(String bODY_TYPE) {
		BODY_TYPE = bODY_TYPE;
	}
	
	
	
}
