package com.zoro.dto;

import java.io.Serializable;

public class CabRequester implements Serializable{

	public CabRequester() {
		// TODO Auto-generated constructor stub
	}
	private int SNO;
	private String REQUEST_ID,REQUESTER_ID,SERVICE_TYPE,BODY_TYPE,DATE_REQUESTED,TRAVEL_DATE,INTERESTED_PACKAGE,REQUEST_SENT_TO,FROM_LOCATION,TO_LOCATION,REQUEST_STATUS,INTERESTED_BRANDS,PICK_UP_LOCATION;
	public int getSNO() {
		return SNO;
	}
	public void setSNO(int sNO) {
		SNO = sNO;
	}
	public String getREQUEST_ID() {
		return REQUEST_ID;
	}
	public void setREQUEST_ID(String rEQUEST_ID) {
		REQUEST_ID = rEQUEST_ID;
	}
	public String getREQUESTER_ID() {
		return REQUESTER_ID;
	}
	public void setREQUESTER_ID(String rEQUESTER_ID) {
		REQUESTER_ID = rEQUESTER_ID;
	}
	public String getSERVICE_TYPE() {
		return SERVICE_TYPE;
	}
	public void setSERVICE_TYPE(String sERVICE_TYPE) {
		SERVICE_TYPE = sERVICE_TYPE;
	}
	public String getBODY_TYPE() {
		return BODY_TYPE;
	}
	public void setBODY_TYPE(String bODY_TYPE) {
		BODY_TYPE = bODY_TYPE;
	}
	public String getDATE_REQUESTED() {
		return DATE_REQUESTED;
	}
	public void setDATE_REQUESTED(String dATE_REQUESTED) {
		DATE_REQUESTED = dATE_REQUESTED;
	}
	public String getTRAVEL_DATE() {
		return TRAVEL_DATE;
	}
	public void setTRAVEL_DATE(String tRAVEL_DATE) {
		TRAVEL_DATE = tRAVEL_DATE;
	}
	public String getINTERESTED_PACKAGE() {
		return INTERESTED_PACKAGE;
	}
	public void setINTERESTED_PACKAGE(String iNTERESTED_PACKAGE) {
		INTERESTED_PACKAGE = iNTERESTED_PACKAGE;
	}
	public String getREQUEST_SENT_TO() {
		return REQUEST_SENT_TO;
	}
	public void setREQUEST_SENT_TO(String rEQUEST_SENT_TO) {
		REQUEST_SENT_TO = rEQUEST_SENT_TO;
	}
	public String getFROM_LOCATION() {
		return FROM_LOCATION;
	}
	public void setFROM_LOCATION(String fROM_LOCATION) {
		FROM_LOCATION = fROM_LOCATION;
	}
	public String getTO_LOCATION() {
		return TO_LOCATION;
	}
	public void setTO_LOCATION(String tO_LOCATION) {
		TO_LOCATION = tO_LOCATION;
	}
	public String getREQUEST_STATUS() {
		return REQUEST_STATUS;
	}
	public void setREQUEST_STATUS(String rEQUEST_STATUS) {
		REQUEST_STATUS = rEQUEST_STATUS;
	}
	public String getINTERESTED_BRANDS() {
		return INTERESTED_BRANDS;
	}
	public void setINTERESTED_BRANDS(String iNTERESTED_BRANDS) {
		INTERESTED_BRANDS = iNTERESTED_BRANDS;
	}
	public String getPICK_UP_LOCATION() {
		return PICK_UP_LOCATION;
	}
	public void setPICK_UP_LOCATION(String pICK_UP_LOCATION) {
		PICK_UP_LOCATION = pICK_UP_LOCATION;
	}
	@Override
	public String toString() {
		return "CabRequester [SNO=" + SNO + ", REQUEST_ID=" + REQUEST_ID + ", REQUESTER_ID=" + REQUESTER_ID
				+ ", SERVICE_TYPE=" + SERVICE_TYPE + ", BODY_TYPE=" + BODY_TYPE + ", DATE_REQUESTED=" + DATE_REQUESTED
				+ ", TRAVEL_DATE=" + TRAVEL_DATE + ", INTERESTED_PACKAGE=" + INTERESTED_PACKAGE + ", REQUEST_SENT_TO="
				+ REQUEST_SENT_TO + ", FROM_LOCATION=" + FROM_LOCATION + ", TO_LOCATION=" + TO_LOCATION
				+ ", REQUEST_STATUS=" + REQUEST_STATUS + ", INTERESTED_BRANDS=" + INTERESTED_BRANDS
				+ ", PICK_UP_LOCATION=" + PICK_UP_LOCATION + "]";
	}
	
}
