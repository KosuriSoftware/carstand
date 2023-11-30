package com.zoro.utilities;

import java.util.Calendar;

public class BookingId {
	
	public String travelerBookingId(String id){
	String genId="";
	int year=Calendar.getInstance().get(Calendar.YEAR);
	String rev="";
	while(year!=0){
		rev=rev+year%10;
		year=year/10;
	}
	genId="ZO"+rev+"TB"+"0"+id;
	return genId;
	}
	
	public String cabBookingId(String id){
		
		String genId="";
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String rev="";
		while(year!=0){
			rev=rev+year%10;
			year=year/10;
		}
		genId="ZO"+rev+"CBK"+"0"+id;
		return genId;
		
	}

}
