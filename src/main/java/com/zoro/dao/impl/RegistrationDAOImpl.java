package com.zoro.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Component;

import com.zoro.dao.RegistrationDAO;
import com.zoro.dto.RegistrationBean;
import com.zoro.mailing.utilities.Mailing;
import com.zoro.mailing.utilities.RegisteredId;
import com.zoro.sms.utilities.OtpGenerator;
import com.zoro.utilities.DBConnection;
import com.zoro.utilities.EncryptDecrypt;
import com.zoro.utilities.IdGen;
@Component
public class RegistrationDAOImpl implements RegistrationDAO {
	
	String email1=null;
	private   Connection con=null;
	private int noOfRecords=0;

	@Override
	public String registerUser(RegistrationBean registerBean) {

		System.out.println("RegistrationDAOImpl registerBean"+registerBean);
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;
		PreparedStatement pst3=null;
		String message="";
		try{
			String id=new IdGen().getId("REGESTERED_ID");
			
			String trId=new RegisteredId().registeredId(id);
			
			String query="insert into registration values(?,?,?,?,?,?,?,?,CURDATE(),?)";
			String query1="insert into login values(?,?,?,?,?,?)";
			
			
			con=DBConnection.getConnection();
			con.setAutoCommit(false);
		    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
		    pst1=con.prepareStatement(query1,pst1.RETURN_GENERATED_KEYS);
		    

		    pst.setInt(1, registerBean.getRegId());
		    //pst.setString(1,id);
		    pst.setString(2, registerBean.getCandidateId());
		    pst.setString(3, registerBean.getAddress());
		    pst.setString(4, registerBean.getEmailId());
		    pst.setString(5, registerBean.getPassword());
		    pst.setString(6, registerBean.getContactNo());
		    pst.setString(7, registerBean.getUserType());
		    pst.setString(8, "INACTIVE");
		    pst.setString(9, trId);
		    int i= pst.executeUpdate();
		    
		    //pst1.setString(1,id);
		    pst1.setInt(1, registerBean.getRegId());
		    pst1.setString(2, registerBean.getEmailId());
		    pst1.setString(3, registerBean.getPassword());
		    pst1.setString(4, "INACTIVE");
		    pst1.setString(5, registerBean.getUserType());
		    pst1.setString(6, registerBean.getContactNo());
		    
		    int j=pst1.executeUpdate();
		    
		   
		    int k=0;
		    if(!registerBean.getUserType().equals("T") && !registerBean.getUserType().equals("AE")){
		    
		    String query2="INSERT INTO members_details VALUES (?, ?, ?, NOW(), ?, NOW(), ?)";
		    pst2=con.prepareStatement(query2,pst2.RETURN_GENERATED_KEYS);
		    
		    //pst2.setString(1,id);
		    pst2.setInt(1, registerBean.getRegId());
		    pst2.setString(2, registerBean.getEmailId());
		    
		    pst2.setString(3, "0");
		    pst2.setString(4, "NEW");
		    pst2.setString(5, "INACTIVE");
		    k=pst2.executeUpdate();
		    }else{
		    	k=1;
		    	pst2=con.prepareStatement("");
		    }
		    System.out.println("query2");
		    String query3="insert into mobile_verification values(?,?,?,?,CURDATE(),?)";
		    pst3=con.prepareStatement(query3,pst3.RETURN_GENERATED_KEYS);
		    
		    //pst3.setString(1,id);
		    pst3.setInt(1, registerBean.getRegId());
		    pst3.setString(2, registerBean.getContactNo());
		    pst3.setString(3, registerBean.getStatus());
		    pst3.setString(4, "INACTIVE");
		    pst3.setString(5, registerBean.getEmailId());
		    int m=pst3.executeUpdate();
		    
		    if(i==0 && j==0 && k==0 && m==0){
			  con.rollback(); 
		   }
		   
		   if(i>0 && j>0 && k>0 && m>0){
		    	con.commit();
		    	message="yes";
		    }else{
		   
		    }

			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();
				pst1.close();
				pst2.close();
				pst3.close();
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return message;
		
		
	
	}
	
	//emial check
	public String regEmailCheck(RegistrationBean registerBean) {
	String status=null;
	  try {
	   String query = "select status from registration where EMAIL_ID='"+registerBean.getEmailId()+"'";
	   con=DBConnection.getConnection();
	   PreparedStatement pst4 = con.prepareStatement(query);
	   ResultSet rs = pst4.executeQuery();
	   if(rs.next()){
		   status = rs.getString("STATUS");  // calling status of user
	   }
	   rs.close();
	   pst4.close();
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }finally{
		  try{
			  con.close();
		  }catch(Exception e){
			  
		  }
	  }
	  return status;
	
	}

	@Override
	public String regMobilecheck(RegistrationBean registerBean) {
		String status=null;
		  try {
		   String query = "select verified_status from mobile_verification where MOBILE_NO ='"+registerBean.getContactNo()+"'";
		   con=DBConnection.getConnection();
		   PreparedStatement pst4 = con.prepareStatement(query);
		   ResultSet rs = pst4.executeQuery();
		   if(rs.next()){
			   status = rs.getString("VERIFIED_STATUS");  // calling status of user
		   }
		   rs.close();
		   pst4.close();
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }finally{
			  try{
				  con.close();
			  }catch(Exception e){
				  
			  }
		  }
		  return status;
		
	}

	@Override
	public String regMobileSendSMS(RegistrationBean registerBean) {
		try {
			String apiKey = "apikey=" + URLEncoder.encode("bpzNEtJGgKE-k8XO8wzS41OuCWD3EeJ5wx8LNL5wEf", "UTF-8");
			registerBean.setMobileOTPMesg(registerBean.getGenOTP()+" "+"is OTP to verify your registered mobile number and valid for 30 mins - www.carstand.in");
			String message = "&message=" + URLEncoder.encode(registerBean.getMobileOTPMesg(), "UTF-8");
			String sender = "&sender=" + URLEncoder.encode("CSTAND", "UTF-8");
			String numbers = "&numbers=91" + URLEncoder.encode(registerBean.getContactNo(), "UTF-8");
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

	@Override
	public String regEmailSendSMS(RegistrationBean registerBean) {
		try {
			 email1 = EncryptDecrypt.encrypt(registerBean.getEmailId());
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		OtpGenerator otp = new OtpGenerator();
		char[] mailotp = otp.OTP(6);
		String msg="Please Verify your Email Id<br>";
		//msg+="<a href='http://carstand.in/MailVerification?email="+email1+"'>Verify your email </a><br><br>";
		msg+=mailotp;
		try{
			new Mailing().send(email1,msg);
		} catch (Exception e) {
		    //Log.error("Error during email encryption or OTP generation", e);
		}
		return msg;
	}
	
	//end

}
