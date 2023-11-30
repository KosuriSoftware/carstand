package com.zoro.utilities;

import java.util.Calendar;

public class CabId {
	
public String CabId(String id){
		
		
		String id1=id;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		
		String genId="ZO"+stryear+"CB"+"00"+id;
		
		return genId;
		
	}

public String routeId(String id){
	
	String id1=id;
	Calendar now = Calendar.getInstance();
	int year = now.get(Calendar.YEAR);
	String stryear=String.valueOf(year);
	
	String genId="ZO"+"CB"+"0"+id;
	
	return genId;
	
}

}
