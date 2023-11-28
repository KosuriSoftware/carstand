package com.zoro.sms.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
 
public class SendTLSms {
	public String sendSms(String fmessage,String phoneno) {
		try {
			// Construct data
			String apiKey = "apikey=" + URLEncoder.encode("bpzNEtJGgKE-k8XO8wzS41OuCWD3EeJ5wx8LNL5wEf", "UTF-8");
						
			String message = "&message=" + URLEncoder.encode(fmessage, "UTF-8");
			String sender = "&sender=" + URLEncoder.encode("CSTAND", "UTF-8");
			String numbers = "&numbers=91" + URLEncoder.encode(phoneno, "UTF-8");
			
			// Send data
			String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
			URL url = new URL(data);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String sResult="";
			while ((line = rd.readLine()) != null) {
			// Process line...
				sResult=sResult+line+" ";
			}
			rd.close();
			System.out.println("RESULT : "+sResult);
			return sResult;
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	
	public String sendMessage(String mobile,String name,String emailid) 
	{
		try {
			// Construct data - message to be edited according to..
			String apiKey = "apikey=" + URLEncoder.encode("bpzNEtJGgKE-k8XO8wzS41OuCWD3EeJ5wx8LNL5wEf", "UTF-8");
		String message = "&message=" + URLEncoder.encode("fmessage", "UTF-8");
		String sender = "&sender=" + URLEncoder.encode("CSTAND", "UTF-8");
		String numbers = "&numbers=91" + URLEncoder.encode(mobile, "UTF-8");
		
			// Send data
			String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;			URL url = new URL(data);			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		String sResult="";
		while ((line = rd.readLine()) != null) {
		// Process line...
				sResult=sResult+line+" ";
		}
		rd.close();
		System.out.println("RESULT : "+sResult);
			return sResult;
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
		
	}
}

	
	
	/*public static void main(String args[]) {
		//TODO Auto-generated method stub
		//int tlcvar=123;
        //new SendTLSms().sendSms("We have received password change request from you. OTP to change password is "+tlcvar+". OTP will expire in next 10 mins.","9440774084");
		
	new SendTLSms().sendSms("1234 is OTP to verify your registered mobile number and valid for 30 mins - www.carstand.in","9440774084");

	System.out.println("hello");
	}
}*/
