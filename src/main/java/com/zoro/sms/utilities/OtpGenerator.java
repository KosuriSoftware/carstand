package com.zoro.sms.utilities;

import java.util.Random;

public  class OtpGenerator {
	
	 public  char[] OTP(int len) 
	 {
//		    System.out.println("Generating OTP using random ()");
//		    System.out.print("Your OTP is:");

		    // Using numeric values
		    String numbers = "8106212590";

		    // Using random method 
		    Random rndm_method = new Random();
		    char[] otp = new char[len];
		    for(int i=0; i<len;i++) {
		      // use of charAt() method : to get character value
		      // use of nextInt() as it is scanning the value as int 
		      otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		    }
		    return otp;
		  }
	 
	public static void main(String[] ar){
		
		 int length = 6;
		 OtpGenerator otp = new OtpGenerator();
		 otp.OTP(length);
		 
		    //System.out.println(OTP(length));
		
	}

}
