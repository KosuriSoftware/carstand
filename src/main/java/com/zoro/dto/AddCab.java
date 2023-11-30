package com.zoro.dto;

public class AddCab {
	
	private int CAB_SEQ_ID;
	private String CAB_REG_NO;
	private String CAB_BRAND;
	private String CAB_MODEL;
	private String MODEL_YEAR;
	private String CURRENT_MILEAGE;
	private String FUEL_TYPE;
	private String BODY_TYPE;
	private String TRANSMISSION;
	private String KM_DRIVEN;
	private String COLOR;
	private String INSURENCE_COMP_NAME;
	private String CERTIFIED_COMP_NAME;
	private String REGISTERED_YEAR;
	private String REGISTERED_CITY;
	private String REGISTERED_STATE;
	private String RC_DOC;
	private String INSURANCE_DOC;
	private String CAB_PHOTO;
	private String CAB_OWNER_ID;
	private String CAB_GEN_ID;
	private String STATUS;
	private String MOBILE_NO;
	private String ADDRESS;
	private String CITY;
	private String STATE;
	private String DISTRICT;
	private String PINCODE;
	private String NO_OF_PASSENGER,CAB_PLATE_STATUS,CAB_ADDED_DATE,SERVICE_TYPE;
	
	//add  fields by danish
	private  int reqId;
	private String planDuration;
	private String plan_expiry_date;
	private int plan_id;
	//End add  fields by danish

	
	
	
	// collect the masters of Cab details
	public int getCAB_SEQ_ID() {
		return CAB_SEQ_ID;
	}
	public void setCAB_SEQ_ID(int cAB_SEQ_ID) {
		CAB_SEQ_ID = cAB_SEQ_ID;
	}
	public String getCAB_REG_NO() {
		return CAB_REG_NO;
	}
	public void setCAB_REG_NO(String cAB_REG_NO) {
		CAB_REG_NO = cAB_REG_NO;
	}
	public String getCAB_BRAND() {
		return CAB_BRAND;
	}
	public void setCAB_BRAND(String cAB_BRAND) {
		CAB_BRAND = cAB_BRAND;
	}
	public String getCAB_MODEL() {
		return CAB_MODEL;
	}
	public void setCAB_MODEL(String cAB_MODEL) {
		CAB_MODEL = cAB_MODEL;
	}
	public String getMODEL_YEAR() {
		return MODEL_YEAR;
	}
	public void setMODEL_YEAR(String mODEL_YEAR) {
		MODEL_YEAR = mODEL_YEAR;
	}
	public String getCURRENT_MILEAGE() {
		return CURRENT_MILEAGE;
	}
	public void setCURRENT_MILEAGE(String cURRENT_MILEAGE) {
		CURRENT_MILEAGE = cURRENT_MILEAGE;
	}
	public String getFUEL_TYPE() {
		return FUEL_TYPE;
	}
	public void setFUEL_TYPE(String fUEL_TYPE) {
		FUEL_TYPE = fUEL_TYPE;
	}
	public String getBODY_TYPE() {
		return BODY_TYPE;
	}
	public void setBODY_TYPE(String bODY_TYPE) {
		BODY_TYPE = bODY_TYPE;
	}
	public String getTRANSMISSION() {
		return TRANSMISSION;
	}
	public void setTRANSMISSION(String tRANSMISSION) {
		TRANSMISSION = tRANSMISSION;
	}
	public String getKM_DRIVEN() {
		return KM_DRIVEN;
	}
	public void setKM_DRIVEN(String kM_DRIVEN) {
		KM_DRIVEN = kM_DRIVEN;
	}
	public String getCOLOR() {
		return COLOR;
	}
	public void setCOLOR(String cOLOR) {
		COLOR = cOLOR;
	}
	public String getINSURENCE_COMP_NAME() {
		return INSURENCE_COMP_NAME;
	}
	public void setINSURENCE_COMP_NAME(String iNSURENCE_COMP_NAME) {
		INSURENCE_COMP_NAME = iNSURENCE_COMP_NAME;
	}
	public String getCERTIFIED_COMP_NAME() {
		return CERTIFIED_COMP_NAME;
	}
	public void setCERTIFIED_COMP_NAME(String cERTIFIED_COMP_NAME) {
		CERTIFIED_COMP_NAME = cERTIFIED_COMP_NAME;
	}
	public String getREGISTERED_YEAR() {
		return REGISTERED_YEAR;
	}
	public void setREGISTERED_YEAR(String rEGISTERED_YEAR) {
		REGISTERED_YEAR = rEGISTERED_YEAR;
	}
	public String getREGISTERED_CITY() {
		return REGISTERED_CITY;
	}
	public void setREGISTERED_CITY(String rEGISTERED_CITY) {
		REGISTERED_CITY = rEGISTERED_CITY;
	}
	public String getREGISTERED_STATE() {
		return REGISTERED_STATE;
	}
	public void setREGISTERED_STATE(String rEGISTERED_STATE) {
		REGISTERED_STATE = rEGISTERED_STATE;
	}
	public String getRC_DOC() {
		return RC_DOC;
	}
	public void setRC_DOC(String rC_DOC) {
		RC_DOC = rC_DOC;
	}
	public String getINSURANCE_DOC() {
		return INSURANCE_DOC;
	}
	public void setINSURANCE_DOC(String iNSURANCE_DOC) {
		INSURANCE_DOC = iNSURANCE_DOC;
	}
	public String getCAB_PHOTO() {
		return CAB_PHOTO;
	}
	public void setCAB_PHOTO(String cAB_PHOTO) {
		CAB_PHOTO = cAB_PHOTO;
	}
	public String getCAB_OWNER_ID() {
		return CAB_OWNER_ID;
	}
	public void setCAB_OWNER_ID(String cAB_OWNER_ID) {
		CAB_OWNER_ID = cAB_OWNER_ID;
	}
	public String getCAB_GEN_ID() {
		return CAB_GEN_ID;
	}
	public void setCAB_GEN_ID(String cAB_GEN_ID) {
		CAB_GEN_ID = cAB_GEN_ID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getMOBILE_NO() {
		return MOBILE_NO;
	}
	public void setMOBILE_NO(String mOBILE_NO) {
		MOBILE_NO = mOBILE_NO;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getDISTRICT() {
		return DISTRICT;
	}
	public void setDISTRICT(String dISTRICT) {
		DISTRICT = dISTRICT;
	}
	public String getPINCODE() {
		return PINCODE;
	}
	public void setPINCODE(String pINCODE) {
		PINCODE = pINCODE;
	}
	public String getNO_OF_PASSENGER() {
		return NO_OF_PASSENGER;
	}
	public void setNO_OF_PASSENGER(String nO_OF_PASSENGER) {
		NO_OF_PASSENGER = nO_OF_PASSENGER;
	}
	public String getCAB_PLATE_STATUS() {
		return CAB_PLATE_STATUS;
	}
	public void setCAB_PLATE_STATUS(String cAB_PLATE_STATUS) {
		CAB_PLATE_STATUS = cAB_PLATE_STATUS;
	}
	public String getCAB_ADDED_DATE() {
		return CAB_ADDED_DATE;
	}
	public void setCAB_ADDED_DATE(String cAB_ADDED_DATE) {
		CAB_ADDED_DATE = cAB_ADDED_DATE;
	}
	public String getSERVICE_TYPE() {
		return SERVICE_TYPE;
	}
	public void setSERVICE_TYPE(String sERVICE_TYPE) {
		SERVICE_TYPE = sERVICE_TYPE;
	}

	//////
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public String getPlanDuration() {
		return planDuration;
	}
	public void setPlanDuration(String planDuration) {
		this.planDuration = planDuration;
	}
	public String getPlan_expiry_date() {
		return plan_expiry_date;
	}
	public void setPlan_expiry_date(String plan_expiry_date) {
		this.plan_expiry_date = plan_expiry_date;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	
	
	
	

}
