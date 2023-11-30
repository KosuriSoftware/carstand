package com.zoro.utilities;

import java.util.Calendar;

public class DriverId {

	public String driverId(String id){
		
		String str="ZO.D.";
		String id1=id;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		String genId=str+stryear+"00"+id;
		
		return genId;
	}
	
	public String driverBookingId(String id){
		
		String id1=id;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		
		String genId="ZO"+stryear+"DBK"+"0"+id;
		
		return genId;
		
	}
	
	public String driverTripId(String id){
		
		String id1=id;
		Calendar now = Calendar.getInstance();
		int mnth = now.get(Calendar.MONTH);
		mnth+=mnth+1;
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		String subyear = stryear.substring(Math.max(stryear.length() - 2, 0));
		
		String genId="ZO"+mnth+subyear+"TR"+"00"+id;
		
		return genId;
	}
	
	public String driverBillId(String id){
		
		String id1=id;
		Calendar now = Calendar.getInstance();
		int mnth = now.get(Calendar.MONTH);
		mnth+=mnth+1;
		int year = now.get(Calendar.YEAR);
		String stryear=String.valueOf(year);
		String subyear = stryear.substring(Math.max(stryear.length() - 2, 0));
		
		String genId="ZO"+mnth+subyear+"DRB"+"00"+id;
		
		return genId;
	}
	
	public String driverChargesId(String id){
		
		return "ZOCH0"+id;
	}
	
	public String driverRoutesId(String id){
		
		return "ZORT0"+id;
	}
	
	public String driverProfileId(String id){
	
		return "ZOPR0"+id;
	}
	
}
