package com.zoro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zoro.dao.LoginDAO;
import com.zoro.utilities.DBConnection;

@Component
public class LoginDAOImpl implements LoginDAO {
	
	@Override
	public Map<String, String> loginCheck(String emailId, String mobileNo) {
		
		Map<String,String> map=new HashMap<String,String>();
		PreparedStatement pst=null;
		String msg="";
		Connection con=null;

			try {
				String query="select rg.EMAIL_ID,rg.STATUS as rgstatus,mb.VERIFIED_STATUS,mb.MOBILE_NO,lg.STATUS as lgstatus,lg.PASSWORD,lg.USER_TYPE as USER_TYPE from registration  rg left join mobile_verification mb on rg.EMAIL_ID=mb.USER_ID left join login lg on rg.EMAIL_ID=lg.EMAIL_ID WHERE rg.EMAIL_ID like BINARY '"+emailId+"' or mb.MOBILE_NO='"+mobileNo+"'";
				System.out.println("Login Check Query "+query);
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				if(rs.next()){
					
					map.put("psw", rs.getString("PASSWORD"));
					map.put("mobile", rs.getString("MOBILE_NO"));
					map.put("email", rs.getString("EMAIL_ID"));
					map.put("regStatus", rs.getString("rgstatus"));
					map.put("loginStatus", rs.getString("lgstatus"));
					map.put("mobileStatus", rs.getString("VERIFIED_STATUS"));
					map.put("usertype", rs.getString("USER_TYPE"));
					System.out.println("Map "+map);
				}
				System.out.println("Map "+map);
				rs.close();
			
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					pst.close();
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return map;
	}
	
	public String updateMobileOtp(String otp,String mobileNo) {
		String msg=null;
		PreparedStatement pst=null;
		Connection con = null;
		try{
			/*set SQL_SAFE_UPDATES=0; */
			String query="update mobile_verification set OTP_CODE='"+otp+"' where MOBILE_NO='"+mobileNo+"'";
			con=DBConnection.getConnection();
			pst=con.prepareStatement(query);
			int i=pst.executeUpdate();
			if(i>0){
				msg="You Mobile Number"+mobileNo+"verification completed - www.carstand.in";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return msg;
	}
	
	public String getUserEmail(String userId) {
		// TODO Auto-generated method stub
		String email=null;
		PreparedStatement pst=null;
		Connection con = null;
		String msg="";

			try {
				String query="select mb.MOBILE_NO,lg.EMAIL_ID from mobile_verification mb  right join login lg on mb.USER_ID=lg.EMAIL_ID WHERE '"+userId+"' in (lg.EMAIL_ID,mb.MOBILE_NO)";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					email=rs.getString("EMAIL_ID");
				}
				
				rs.close();
			
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					pst.close();
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return email;
	}
	
	public Map<String, String> getMobileVerificationDetails(String mobileNo) {
		PreparedStatement pst=null;
		Connection con = null;
		Map<String,String> mobMap=new HashMap<String,String>();

			try {
				String query="SELECT MOBILE_NO,USER_ID FROM mobile_verification WHERE MOBILE_NO='"+mobileNo+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
				mobMap.put("email", rs.getString("USER_ID"));
				mobMap.put("mobileNo", rs.getString("MOBILE_NO"));
					
				}
				
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					pst.close();
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
		return mobMap;
	}

}
