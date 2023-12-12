package com.zoro.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zoro.dao.MobileVerificationDAO;
import com.zoro.dto.MobileVerificationBean;
import com.zoro.dto.RegistrationBean;
import com.zoro.utilities.DBConnection;

@Component
public class MobileVerificationDAOImpl implements MobileVerificationDAO{
	Connection con=null;
	Statement st=null;
	Statement st1=null;
	@Override
	public String verifyUser(MobileVerificationBean mobileVerifyBean) {
	
		String msg=null;
		Map<String,String> map=checkMobileVerification(mobileVerifyBean);
		System.out.println("mob mobBean"+mobileVerifyBean);
		if(map.get("otp")!=null && !map.get("otp").equals(mobileVerifyBean.getOtp())){
		msg="Incorrect Otp,please enter correct otp!.";	
		}else if(map.get("otp")!=null && map.get("otp").equals(mobileVerifyBean.getOtp())){
			msg=updateMobileStatus(mobileVerifyBean);
		}else {
			msg="This combination doesn't exist";
		}
		System.out.println("mobService mobBean"+msg);
		return msg;
	}

	private String updateMobileStatus(MobileVerificationBean mobileVerifyBean) {
		String message="";
		try{
			String query="update mobile_verification set VERIFIED_STATUS='ACTIVE' where  USER_ID='"+mobileVerifyBean.getEmailId()+"' and MOBILE_NO='"+mobileVerifyBean.getMobileNumber()+"' and OTP_CODE='"+mobileVerifyBean.getOtp()+"'";
			String query1="update login set STATUS='ACTIVE' where  EMAIL_ID='"+mobileVerifyBean.getEmailId()+"'";
			System.out.println("mobService mobBean"+mobileVerifyBean);
			con=DBConnection.getConnection();
			con.setAutoCommit(false);
			st=con.createStatement();
			st1=con.createStatement();
			
				int i=((java.sql.Statement) st).executeUpdate(query);
				int j=((java.sql.Statement) st).executeUpdate(query1);
				
				if(i==0 && j==0){
					con.rollback();
				}
				
				if(i>0 && j>0){
					con.commit();
					message="verified successfully please login here";
				}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				//st.close();
				//st1.close();
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("mobService mobBean"+message);
		return message;
	}

	private Map<String, String> checkMobileVerification(MobileVerificationBean mobileVerifyBean) {
		  Map<String,String> map=new HashMap<String,String>();
		  String status=null;
		  try {
		   String query = "select * from mobile_verification where USER_ID='"+mobileVerifyBean.getEmailId()+"' and MOBILE_NO='"+mobileVerifyBean.getMobileNumber()+"'";
		   con=DBConnection.getConnection();
		   PreparedStatement pst=null;
			PreparedStatement pst1=null;
			PreparedStatement pst2=null;
			PreparedStatement pst3=null;
		   PreparedStatement preparedStatement = con.prepareStatement(query);
		   ResultSet rs = preparedStatement.executeQuery();
		   while(rs.next()){
			   map.put("email", rs.getString("USER_ID"));
			   map.put("mobileNo", rs.getString("MOBILE_NO"));
			   map.put("otp", rs.getString("OTP_CODE"));
		   }
		   rs.close();
		   preparedStatement.close();
		   con.close();
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }finally{
			  try{
				con.close();  
			  }catch(Exception e){
				  
			  }
		  }
		  return map;
	}
	
}

