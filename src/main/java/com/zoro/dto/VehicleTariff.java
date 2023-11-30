package com.zoro.dto;

import java.io.Serializable;

public class VehicleTariff implements Serializable {

	private int SNO;
	public String BODY_TYPE,BASIC_PRICE,PRICE_PER_KM,PRICE_PER_8HOURS,PRICE_PER_HOUR,PRICE_PER_DAY,WAITING_CHARGES,DISCOUNT,SERVICE_TYPE,CITY,STATE,DATE_UPDATED,EMPTY6,EMPTY7,EMPTY8,EMPTY9,EMPTY10,UPDATED_BY;
	
	public int getSNO() {
		return SNO;
	}
	public void setSNO(int sNO) {
		SNO = sNO;
	}
	public String getBODY_TYPE() {
		return BODY_TYPE;
	}
	public void setBODY_TYPE(String bODY_TYPE) {
		BODY_TYPE = bODY_TYPE;
	}
	public String getBASIC_PRICE() {
		return BASIC_PRICE;
	}
	public void setBASIC_PRICE(String bASIC_PRICE) {
		BASIC_PRICE = bASIC_PRICE;
	}
	public String getPRICE_PER_KM() {
		return PRICE_PER_KM;
	}
	public void setPRICE_PER_KM(String pRICE_PER_KM) {
		PRICE_PER_KM = pRICE_PER_KM;
	}
	public String getPRICE_PER_8HOURS() {
		return PRICE_PER_8HOURS;
	}
	public void setPRICE_PER_8HOURS(String pRICE_PER_8HOURS) {
		PRICE_PER_8HOURS = pRICE_PER_8HOURS;
	}
	public String getPRICE_PER_HOUR() {
		return PRICE_PER_HOUR;
	}
	public void setPRICE_PER_HOUR(String pRICE_PER_HOUR) {
		PRICE_PER_HOUR = pRICE_PER_HOUR;
	}
	public String getPRICE_PER_DAY() {
		return PRICE_PER_DAY;
	}
	public void setPRICE_PER_DAY(String pRICE_PER_DAY) {
		PRICE_PER_DAY = pRICE_PER_DAY;
	}
	public String getWAITING_CHARGES() {
		return WAITING_CHARGES;
	}
	public void setWAITING_CHARGES(String wAITING_CHARGES) {
		WAITING_CHARGES = wAITING_CHARGES;
	}
	public String getDISCOUNT() {
		return DISCOUNT;
	}
	public void setDISCOUNT(String dISCOUNT) {
		DISCOUNT = dISCOUNT;
	}
	
	public String getSERVICE_TYPE() {
		return SERVICE_TYPE;
	}
	public void setSERVICE_TYPE(String sERVICE_TYPE) {
		SERVICE_TYPE = sERVICE_TYPE;
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
	public String getDATE_UPDATED() {
		return DATE_UPDATED;
	}
	public void setDATE_UPDATED(String dATE_UPDATED) {
		DATE_UPDATED = dATE_UPDATED;
	}
	public String getEMPTY6() {
		return EMPTY6;
	}
	public void setEMPTY6(String eMPTY6) {
		EMPTY6 = eMPTY6;
	}
	public String getEMPTY7() {
		return EMPTY7;
	}
	public void setEMPTY7(String eMPTY7) {
		EMPTY7 = eMPTY7;
	}
	public String getEMPTY8() {
		return EMPTY8;
	}
	public void setEMPTY8(String eMPTY8) {
		EMPTY8 = eMPTY8;
	}
	public String getEMPTY9() {
		return EMPTY9;
	}
	public void setEMPTY9(String eMPTY9) {
		EMPTY9 = eMPTY9;
	}
	public String getEMPTY10() {
		return EMPTY10;
	}
	public void setEMPTY10(String eMPTY10) {
		EMPTY10 = eMPTY10;
	}
	public String getUPDATED_BY() {
		return UPDATED_BY;
	}
	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}
	@Override
	public String toString() {
		return "VehicleTariff [SNO=" + SNO + ", BODY_TYPE=" + BODY_TYPE + ", BASIC_PRICE=" + BASIC_PRICE
				+ ", PRICE_PER_KM=" + PRICE_PER_KM + ", PRICE_PER_8HOURS=" + PRICE_PER_8HOURS + ", PRICE_PER_HOUR="
				+ PRICE_PER_HOUR + ", PRICE_PER_DAY=" + PRICE_PER_DAY + ", WAITING_CHARGES=" + WAITING_CHARGES
				+ ", DISCOUNT=" + DISCOUNT + ", SERVICE_TYPE=" + SERVICE_TYPE + ", CITY=" + CITY + ", STATE=" + STATE
				+ ", DATE_UPDATED=" + DATE_UPDATED + ", EMPTY6=" + EMPTY6 + ", EMPTY7=" + EMPTY7 + ", EMPTY8=" + EMPTY8
				+ ", EMPTY9=" + EMPTY9 + ", EMPTY10=" + EMPTY10 + ", UPDATED_BY=" + UPDATED_BY + "]";
	}
}
