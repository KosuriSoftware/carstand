package com.zoro.mailing.utilities;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import com.zoro.sms.utilities.OtpGenerator;
import com.zoro.utilities.EncryptDecrypt;

public class VerificationMail {
	
	public void verifyMail(String email){
		String email1=null;
		try {
			 email1 = EncryptDecrypt.encrypt(email);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 OtpGenerator otp = new OtpGenerator();
		 char[] smsotp = otp.OTP(6);
		
		String msg="Please Verify your Email Id<br>";
		msg+="<a href='http://carstand.in/MailVerification?email="+email1+"'>Verify your email </a><br><br>";
//		msg+="<a href='http://localhost:8080/CarStand_Jsp/MailVerification?email="+email1+"'>Verify your email </a><br><br>";
//		msg+="mobile verificaton OTP"+ Arrays.toString(smsotp)+"";
		try{
		new Mailing().send(email,msg);
	
		}catch(Exception e){
			
		}
	}
	
	public void resetPsw(String email){
		
		String email1=null;
		try {
			email1= EncryptDecrypt.encrypt(email);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String msg="For reset your password please";
		msg+="<a href='http://carstand.in/resetPsw.jsp?email="+email1+"'>click here </a><br><br>";
//		msg+="<a href='http://localhost:8080/CarStand_Jsp/resetPsw.jsp?email="+email1+"'>click here </a><br><br>";
		try{
		new Mailing().send(email,msg);
		}catch(Exception e){
			
		}
		
	}
	
	
}
