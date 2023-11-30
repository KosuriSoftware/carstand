package com.zoro.mailing.utilities;


import com.zoro.dao.impl.CabDAOImpl;
import com.zoro.dto.AddCab;
import com.zoro.dto.DriverVerificationFeddback;
import com.zoro.sms.utilities.SendTLSms;

public class ActivationMailing {
	

	public void sendCabVerifiedmail(String email,String ownerId,String cabId,String feedBack,String status){
		
		String msg="Your Cab Verification info!.<br>";
		msg+="Cab Id"+" : "+cabId+"<br>";
		msg+="Admin Feedback"+" : "+feedBack+"<br>";
		msg+="Cab Status"+" : "+status+"<br>";
		//String cab_verify_sms= "Your cab"+" "+cabId+" "+"has been verified successfully. Please login and update routes now - www.carstand.in";

		try{
		new Mailing().send(ownerId,msg);
		
		}catch(Exception e){
			
		}
		
//		String cab_verify_sms= "Your cab"+" "+cabId+" "+"has been verified successfully. Please login and update routes now - www.carstand.in";
		String cab_verify_sms= "Your cab "+cabId+" has been verified successfully. Please login and update routes now - www.carstand.in";
         
		CabDAOImpl mobno = new CabDAOImpl();
		String MobileNo = mobno.getCabContactNo(cabId);
		System.out.println(MobileNo);
		SendTLSms sms = new SendTLSms();
		sms.sendSms(cab_verify_sms,MobileNo);
	}
	
	public void cabVerificationMail(String email,String cabId){
		
		String msg="Your Cab has been added to carstand.in but it's in inactive mode<br>";
		msg+="please wait for cab verification. Admin will verify cab as soon as possible<br>";
		msg+="Cab Id"+" : "+cabId+"<br>";
		msg+="Cab Status"+" : "+"INACTIVE";
		
		try{
		new Mailing().send(email,msg);
		}catch(Exception e){
			
		}
	}
	
	public void activationOfRegUserByAdmin(String email,String status){
		
		String msg="You are "+"  "+status+"  "+"Successfully!.";
		
		try{
		new Mailing().send(email,msg);
		}catch(Exception e){
			
		}
	}
	
	public void activationOfDriverByAdmin(DriverVerificationFeddback driverVerify,String status){
		
		String msg="You are "+"  "+status+"  "+" by zorocabs admin";
		msg+="verification Message:"+"  "+driverVerify.getFEED_BACK();
		
		try{
		new Mailing().send(driverVerify.getDRIVER_EMAIL(),msg);
		}catch(Exception e){
			
		}
	}
	
  //add by danish
  public void approvedRequestByAdmin(int requestId, String email,String totalAmount,int cabId){			   
 
	    String msg="Your renewal order Id is "+String.valueOf(requestId)+" and Order total amount is "+totalAmount+". Please login to check your order details - www.carstand.in";

	    //		String msg="Your Membership Request "+"  "+status+"  "+"Successfully!.";
//		String msg="1234 is OTP to verify your registered mobile number and valid for 30 mins - www.carstand.in";		
		try{
		new Mailing().send(email,msg);
		
		String MobileNo = new CabDAOImpl().getCabContactNoByCabId(cabId);
		SendTLSms sms = new SendTLSms();
		sms.sendSms(msg,MobileNo);

		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
//end by danish
}



