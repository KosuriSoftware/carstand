package com.zoro.mailing.utilities;

import java.util.Calendar;

public class RegisteredId {
	
	public String registeredId(String id){
		
		
		String id1=id;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		
		String genId="ZO"+stryear+"RG"+"00"+id1; 
		
		return genId;
		
	}

}
