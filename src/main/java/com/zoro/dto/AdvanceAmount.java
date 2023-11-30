package com.zoro.dto;

public class AdvanceAmount {

	int TAA_SEQ_ID;
	String TRAVELLER_ID,AMOUNT_PAID,PAID_DATE,REFUND_DATE,PAYMENT_DONE_AGAINST,TRACKING_ID,BANK_REF_NO,BOOKING_ID,PAYMENT_STATUS;

	public int getTAA_SEQ_ID() {
		return TAA_SEQ_ID;
	}

	public void setTAA_SEQ_ID(int tAA_SEQ_ID) {
		TAA_SEQ_ID = tAA_SEQ_ID;
	}

	public String getTRAVELLER_ID() {
		return TRAVELLER_ID;
	}

	public void setTRAVELLER_ID(String tRAVELLER_ID) {
		TRAVELLER_ID = tRAVELLER_ID;
	}

	public String getAMOUNT_PAID() {
		return AMOUNT_PAID;
	}

	public void setAMOUNT_PAID(String aMOUNT_PAID) {
		AMOUNT_PAID = aMOUNT_PAID;
	}

	public String getPAID_DATE() {
		return PAID_DATE;
	}

	public void setPAID_DATE(String pAID_DATE) {
		PAID_DATE = pAID_DATE;
	}

	public String getREFUND_DATE() {
		return REFUND_DATE;
	}

	public void setREFUND_DATE(String rEFUND_DATE) {
		REFUND_DATE = rEFUND_DATE;
	}

	public String getPAYMENT_DONE_AGAINST() {
		return PAYMENT_DONE_AGAINST;
	}

	public void setPAYMENT_DONE_AGAINST(String pAYMENT_DONE_AGAINST) {
		PAYMENT_DONE_AGAINST = pAYMENT_DONE_AGAINST;
	}


	public String getPAYMENT_STATUS() {
		return PAYMENT_STATUS;
	}

	public void setPAYMENT_STATUS(String pAYMENT_STATUS) {
		PAYMENT_STATUS = pAYMENT_STATUS;
	}

	public String getTRACKING_ID() {
		return TRACKING_ID;
	}

	public void setTRACKING_ID(String tRACKING_ID) {
		TRACKING_ID = tRACKING_ID;
	}

	public String getBANK_REF_NO() {
		return BANK_REF_NO;
	}

	public void setBANK_REF_NO(String bANK_REF_NO) {
		BANK_REF_NO = bANK_REF_NO;
	}

	public String getBOOKING_ID() {
		return BOOKING_ID;
	}

	public void setBOOKING_ID(String bOOKING_ID) {
		BOOKING_ID = bOOKING_ID;
	}

	@Override
	public String toString() {
		return "AdvanceAmount [TAA_SEQ_ID=" + TAA_SEQ_ID + ", TRAVELLER_ID=" + TRAVELLER_ID + ", AMOUNT_PAID="
				+ AMOUNT_PAID + ", PAID_DATE=" + PAID_DATE + ", REFUND_DATE=" + REFUND_DATE + ", PAYMENT_DONE_AGAINST="
				+ PAYMENT_DONE_AGAINST + ", TRACKING_ID=" + TRACKING_ID + ", BANK_REF_NO=" + BANK_REF_NO
				+ ", BOOKING_ID=" + BOOKING_ID + ", PAYMENT_STATUS=" + PAYMENT_STATUS + "]";
	}
}
