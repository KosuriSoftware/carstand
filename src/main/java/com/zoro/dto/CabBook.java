package com.zoro.dto;

public class CabBook {
	
	private int CAB_BOOKING_SEQ_NO;
	private String USER_EMAIL;
	private String CONTACT_NO;
	private String PICK_UP_ADDRESS;
	private String BOOKING_ID;
	private String BOOKING_DATE;
	private String BOOKING_STATUS;
	private String PASSWORD;
	private String CAB_ID;
	private String CAB_OWNER_ID;
	private String DROPPING_ADDRESS;
	private String CAB_ROUTE_ID;
	private String NAME;
	private String RETURNED_DATE;
	private String CAB_SERVICE_TYPE;
	private String FROM_CITY;
	private String TO_CITY;
	
	
	public String getFROM_CITY() {
		return FROM_CITY;
	}
	public void setFROM_CITY(String fROM_CITY) {
		FROM_CITY = fROM_CITY;
	}
	public String getTO_CITY() {
		return TO_CITY;
	}
	public void setTO_CITY(String tO_CITY) {
		TO_CITY = tO_CITY;
	}
	public String getPICK_UP_TIME() {
		return PICK_UP_TIME;
	}
	public void setPICK_UP_TIME(String pICK_UP_TIME) {
		PICK_UP_TIME = pICK_UP_TIME;
	}
	private String TRAVELING_DATE;
	private String PICK_UP_TIME;
	
	public int getCAB_BOOKING_SEQ_NO() {
		return CAB_BOOKING_SEQ_NO;
	}
	public void setCAB_BOOKING_SEQ_NO(int cAB_BOOKING_SEQ_NO) {
		CAB_BOOKING_SEQ_NO = cAB_BOOKING_SEQ_NO;
	}
	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}
	public void setUSER_EMAIL(String uSER_EMAIL) {
		USER_EMAIL = uSER_EMAIL;
	}
	public String getCONTACT_NO() {
		return CONTACT_NO;
	}
	public void setCONTACT_NO(String cONTACT_NO) {
		CONTACT_NO = cONTACT_NO;
	}
	public String getPICK_UP_ADDRESS() {
		return PICK_UP_ADDRESS;
	}
	public void setPICK_UP_ADDRESS(String pICK_UP_ADDRESS) {
		PICK_UP_ADDRESS = pICK_UP_ADDRESS;
	}
	public String getBOOKING_ID() {
		return BOOKING_ID;
	}
	public void setBOOKING_ID(String bOOKING_ID) {
		BOOKING_ID = bOOKING_ID;
	}
	public String getBOOKING_DATE() {
		return BOOKING_DATE;
	}
	public void setBOOKING_DATE(String bOOKING_DATE) {
		BOOKING_DATE = bOOKING_DATE;
	}
	public String getBOOKING_STATUS() {
		return BOOKING_STATUS;
	}
	public void setBOOKING_STATUS(String bOOKING_STATUS) {
		BOOKING_STATUS = bOOKING_STATUS;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getCAB_ID() {
		return CAB_ID;
	}
	public void setCAB_ID(String cAB_ID) {
		CAB_ID = cAB_ID;
	}
	public String getCAB_OWNER_ID() {
		return CAB_OWNER_ID;
	}
	public void setCAB_OWNER_ID(String cAB_OWNER_ID) {
		CAB_OWNER_ID = cAB_OWNER_ID;
	}
	public String getDROPPING_ADDRESS() {
		return DROPPING_ADDRESS;
	}
	public void setDROPPING_ADDRESS(String dROPPING_ADDRESS) {
		DROPPING_ADDRESS = dROPPING_ADDRESS;
	}
	public String getCAB_ROUTE_ID() {
		return CAB_ROUTE_ID;
	}
	public void setCAB_ROUTE_ID(String cAB_ROUTE_ID) {
		CAB_ROUTE_ID = cAB_ROUTE_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getRETURNED_DATE() {
		return RETURNED_DATE;
	}
	public void setRETURNED_DATE(String rETURNED_DATE) {
		RETURNED_DATE = rETURNED_DATE;
	}
	public String getCAB_SERVICE_TYPE() {
		return CAB_SERVICE_TYPE;
	}
	public void setCAB_SERVICE_TYPE(String cAB_SERVICE_TYPE) {
		CAB_SERVICE_TYPE = cAB_SERVICE_TYPE;
	}
	public String getTRAVELING_DATE() {
		return TRAVELING_DATE;
	}
	public void setTRAVELING_DATE(String tRAVELING_DATE) {
		TRAVELING_DATE = tRAVELING_DATE;
	}
	@Override
	public String toString() {
		return "CabBook [CAB_BOOKING_SEQ_NO=" + CAB_BOOKING_SEQ_NO + ", USER_EMAIL=" + USER_EMAIL + ", CONTACT_NO="
				+ CONTACT_NO + ", PICK_UP_ADDRESS=" + PICK_UP_ADDRESS + ", BOOKING_ID=" + BOOKING_ID + ", BOOKING_DATE="
				+ BOOKING_DATE + ", BOOKING_STATUS=" + BOOKING_STATUS + ", PASSWORD=" + PASSWORD + ", CAB_ID=" + CAB_ID
				+ ", CAB_OWNER_ID=" + CAB_OWNER_ID + ", DROPPING_ADDRESS=" + DROPPING_ADDRESS + ", CAB_ROUTE_ID="
				+ CAB_ROUTE_ID + ", NAME=" + NAME + ", RETURNED_DATE=" + RETURNED_DATE + ", CAB_SERVICE_TYPE="
				+ CAB_SERVICE_TYPE + ", FROM_CITY=" + FROM_CITY + ", TO_CITY=" + TO_CITY + ", TRAVELING_DATE="
				+ TRAVELING_DATE + ", PICK_UP_TIME=" + PICK_UP_TIME + "]";
	}

}
