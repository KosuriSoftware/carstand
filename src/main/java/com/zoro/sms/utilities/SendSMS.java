package com.zoro.sms.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
/*Text Local SMS*/

public class SendSMS {
	
	public String sendSms(String message,String phoneno) {
		try {
			// Construct data
			String apiKey = "apikey=" + "bpzNEtJGgKE-k8XO8wzS41OuCWD3EeJ5wx8LNL5wEf";
			message = "&message=" + message;
			String sender = "&sender=" + "CSTAND";
			String numbers = "&numbers=" + phoneno;
			
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			System.out.println("result"+line);
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		String otp="1234";
		new SendSMS().sendSms("We have received password change request from you. OTP to change password is "+otp+". OTP will expire in next 10 mins.","919440774084");
		System.out.println("hello");
	}
}