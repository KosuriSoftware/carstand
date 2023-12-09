package com.zoro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.zoro.dao.RegistrationDAO;
import com.zoro.dto.RegistrationBean;
import com.zoro.mailing.utilities.RegisteredId;
import com.zoro.utilities.DBConnection;
import com.zoro.utilities.IdGen;

public class RegistrationDAOImpl implements RegistrationDAO {
	

	private   Connection con=null;
	private int noOfRecords=0;

	@Override
	public String registerUser(RegistrationBean registerBean) {

		// TODO Auto-generated method stub
		
		
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

}
