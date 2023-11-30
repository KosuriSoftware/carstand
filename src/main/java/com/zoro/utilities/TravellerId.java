package com.zoro.utilities;

import java.util.Calendar;

public class TravellerId {
	
	public String travellerId(String id){
		
		
		String id1=id;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		
		String genId="ZO"+stryear+"TR"+"00"+id;
		
		return genId;
		
	}

}
