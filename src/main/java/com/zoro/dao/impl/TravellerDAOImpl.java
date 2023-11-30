package com.zoro.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.zoro.dao.TravellerDAO;
import com.zoro.dto.RegistrationBean;
import com.zoro.dto.TravelerBooking;
import com.zoro.dto.Travellers;
import com.zoro.utilities.BookingId;
import com.zoro.utilities.DBConnection;
import com.zoro.utilities.IdGen;
import com.zoro.utilities.SQLDate;
import com.zoro.utilities.TravellerId;

public class TravellerDAOImpl implements TravellerDAO {
	
	private   Connection con=null;
	private int noOfRecords=0;

	public String insertTravellerPost(Travellers traveller) {
		// TODO Auto-generated method stub
		
		PreparedStatement pst=null;
		String message=null;
		
		
		try{
			
//			String date=new SQLDate().getInDate(traveller.getTRAVELING_DATE());
			
			String id=new IdGen().getId("TRAVELLER_ID");
			
			String trId=new TravellerId().travellerId(id);
			
			String query="insert into travellers values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			
			con=DBConnection.getConnection();
		    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
		    
		    pst.setInt(1, traveller.getBOOKING_SEQ_ID());
		    pst.setString(2, traveller.getVEHICLE_TYPE());
		    pst.setString(3, traveller.getNO_OF_PASSENGER());
		    pst.setString(4, traveller.getPICK_UP_CITY());
		    pst.setString(5, traveller.getPICK_UP_ADDRESS());
		    pst.setString(6, traveller.getDESTINATION_CITY());
		    pst.setString(7, traveller.getDESTINATION_ADDRESS());
		    pst.setString(8, traveller.getTRAVELING_DATE());
		    pst.setString(9, traveller.getOFFER_PRICE());
		    pst.setString(10, trId);
		    pst.setString(11, "ACTIVE");
		    pst.setString(12, traveller.getTRAVELLER_EMAIL());
		    int i= pst.executeUpdate();
		
		    if(i>0){
		    	message="Your Travelling Details Are Added Successfully....";
		    }else{
		   
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
		return message;
		
	}

	public RegistrationBean viewProfile(String email) {
		// TODO Auto-generated method stub
		RegistrationBean bean=new RegistrationBean();
		PreparedStatement pst=null;
		String msg="";

			try {
				String query="SELECT * FROM registration where EMAIL_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			System.out.println("query  "+query);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					bean.setREG_ID(rs.getInt("REG_ID"));
					bean.setCANDIDATE_NAME(rs.getString("CANDIDATE_NAME"));
					bean.setADDRESS(rs.getString("ADDRESS"));
					bean.setEMAIL_ID(rs.getString("EMAIL_ID"));
					bean.setPASSWORD(rs.getString("PASSWORD"));
					bean.setCONTACT_NO(rs.getString("CONTACT_NO"));
					bean.setUSER_TYPE(rs.getString("USER_TYPE"));
					bean.setREG_DATE(rs.getString("REG_DATE"));
					bean.setSTATUS(rs.getString("STATUS"));
					bean.setCANDIDATE_ID(rs.getString("CANDIDATE_ID"));
					
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
			return bean;
		
	}
	
	public String updateProfile(RegistrationBean reg) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;

		try{
			/*set SQL_SAFE_UPDATES=0; */
			String query="update registration set CANDIDATE_NAME='"+reg.getCANDIDATE_NAME()+"',ADDRESS='"+reg.getADDRESS()+"',CONTACT_NO='"+reg.getCONTACT_NO()+"',USER_TYPE='"+reg.getUSER_TYPE()+"' where  EMAIL_ID='"+reg.getEMAIL_ID()+"'";
			String query1="update login set USER_TYPE='"+reg.getUSER_TYPE()+"' where EMAIL_ID='"+reg.getEMAIL_ID()+"'";
			con=DBConnection.getConnection();
			pst=con.prepareStatement(query);
			con.setAutoCommit(false);
			pst1=con.prepareStatement(query1);
			int i=pst.executeUpdate();
			int k=pst1.executeUpdate();
			
			if(i>0 && k>0){
				con.commit();
				msg=" Updated Successfully....";
			}else{
				con.rollback();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();pst1.close();
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return msg;
	}

	public Map<String, Set<String>> getTravellerCityAndVehicleType() {
		// TODO Auto-generated method stub
		Map<String,Set<String>> traveller=new TreeMap<String,Set<String>>();
		
		Set<String> fromset=new TreeSet<String>();
		Set<String> toset=new TreeSet<String>();
		Set<String> vehicleSet=new TreeSet<String>();
		
		String msg=null;
		PreparedStatement pst=null;

			try {
				String query="SELECT PICK_UP_CITY,DESTINATION_CITY,VEHICLE_TYPE FROM travellers where PICK_UP_CITY is not null and DESTINATION_CITY is not null and VEHICLE_TYPE is not null";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					fromset.add(rs.getString("PICK_UP_CITY"));
					toset.add(rs.getString("DESTINATION_CITY"));
					vehicleSet.add(rs.getString("VEHICLE_TYPE"));
				}
				
				traveller.put("fromCity", fromset);
				traveller.put("toCity", toset);
				traveller.put("vehicleType", vehicleSet);
				
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
		
		return traveller;
	}

	public List<Travellers> searchTraveller(String query) {
		// TODO Auto-generated method stub
		List<Travellers> travellerList=new ArrayList<Travellers>();
		PreparedStatement pst=null;
			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					Travellers bean=new Travellers();
					bean.setBOOKING_SEQ_ID(rs.getInt("BOOKING_SEQ_ID"));
					bean.setVEHICLE_TYPE(rs.getString("VEHICLE_TYPE"));
					bean.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					bean.setPICK_UP_CITY(rs.getString("PICK_UP_CITY"));
					bean.setPICK_UP_ADDRESS(rs.getString("PICK_UP_ADDRESS"));
					bean.setDESTINATION_CITY(rs.getString("DESTINATION_CITY"));
					bean.setDESTINATION_ADDRESS(rs.getString("DESTINATION_ADDRESS"));
					bean.setTRAVELING_DATE(rs.getString("TRAVELING_DATE"));
					bean.setOFFER_PRICE(rs.getString("OFFER_PRICE"));
					bean.setTRAVELING_ID(rs.getString("TRAVELING_ID"));
					bean.setTRAVELLER_EMAIL(rs.getString("TRAVELLER_EMAIL"));
					bean.setSTATUS(rs.getString("STATUS"));
					
					travellerList.add(bean);
					
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
			return travellerList;
	}

	public List<Travellers> searchHTraveller(String fromCity, String toCity, String date, String vehicleType,String location,String noOfPas) {
		
		/*String date="";
		if(searchDate!=null && !searchDate.equals("") && !searchDate.equals("null")){
		date=new SQLDate().getInDate(searchDate);
		}*/
		
		// TODO Auto-generated method stub
		String query=null;
		
		if(fromCity==null && toCity==null && date.equals("") && vehicleType==null){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(fromCity!=null && toCity!=null && (location!=null && !location.equals(""))){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and PICK_UP_ADDRESS='"+location+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(fromCity!=null && toCity!=null && noOfPas!=null){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and NO_OF_PASSENGER='"+noOfPas+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(fromCity!=null && toCity!=null && location!=null && noOfPas!=null){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and PICK_UP_ADDRESS='"+location+"' and NO_OF_PASSENGER='"+noOfPas+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(!fromCity.equals("") && !toCity.equals("") && date.equals("") && vehicleType.equals("")){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(!fromCity.equals("") && !toCity.equals("") && !date.equals("") && vehicleType.equals("")){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and tr.traveling_date='"+date+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(!fromCity.equals("") && !toCity.equals("") && !date.equals("") && !vehicleType.equals("")){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and tr.traveling_date='"+date+"' and tr.vehicle_type='"+vehicleType+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else if(!fromCity.equals("") && !toCity.equals("") && date.equals("") && !vehicleType.equals("")){
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.pick_up_city='"+fromCity+"' and tr.destination_city='"+toCity+"' and tr.vehicle_type='"+vehicleType+"' and tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}else{
			
			query="select tr.*,reg.STATUS from travellers tr left join registration reg on tr.TRAVELLER_EMAIL=reg.EMAIL_ID where tr.STATUS='ACTIVE' and reg.STATUS='ACTIVE'";
			
		}
		
		List<Travellers> traveler=searchTraveller(query);
		return traveler;
	}

	public String getCabId(String email, String cabId,String trId) {
		// TODO Auto-generated method stub

		String msg=null,carId=null;
		PreparedStatement pst=null;

			try {
				String query="SELECT CAB_ID FROM traveler_booking where CAB_OWNER_ID='"+email+"' and CAB_ID='"+cabId+"' and TRAVELER_ID='"+trId+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
				
					carId=rs.getString("CAB_ID");
					
				}
				if(carId!=null){
					msg="already booked this traveler on this cab";
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
		
		return msg;
	}

	public String insertTravelerBooking(List<String> list) {
		// TODO Auto-generated method stub
		
		PreparedStatement pst=null;
		String msg=null;
		try{
			String id=new IdGen().getId("TRAVELER_BOOKING_ID");
			String bookingId=new BookingId().travelerBookingId(id);
			String query="insert into traveler_booking values(?,?,?,?,?,?,?,CURDATE(),?)";
			con=DBConnection.getConnection();
			pst=con.prepareStatement(query);
			
			for(int i=0;i<list.size();i++){
			pst.setInt(1, 0);
			pst.setString(2, list.get(5));
			pst.setString(3, list.get(0));
			pst.setString(4, list.get(3));
			pst.setString(5, list.get(4));
			pst.setString(6, list.get(2));
			pst.setString(7, bookingId);
			pst.setString(8, "BOOKED");
			}
			
			int i=pst.executeUpdate();
			if(i>0){
			msg="thank you for booking this traveler,traveler will contact you soon!.";	
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

	public List<String> getTravellerBookingDetails(String cabId, String travellerId) {
		// TODO Auto-generated method stub
		List<String> list=new ArrayList<String>();
		PreparedStatement pst=null;

			try {
				String query="SELECT * FROM traveler_booking where CAB_ID='"+cabId+"' and TRAVELER_ID='"+travellerId+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
				
					list.add(rs.getString("CAB_ID"));
					list.add(rs.getString("CAB_OWNER_ID"));
					list.add(rs.getString("OFFER_PRICE"));
					list.add(rs.getString("BOOKING_ID"));
					list.add(rs.getString("BOOKING_DATE"));
					
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
		
		return list;
	}

	public Travellers getTravelersDetails(String trId) {
		// TODO Auto-generated method stub
		String query="select * from travellers where TRAVELING_ID='"+trId+"'";
		List<Travellers> traveler=searchTraveller(query);
		Travellers bean=null;
		
		Iterator it=traveler.iterator();
		
		while(it.hasNext()){
			bean=(Travellers)it.next();
		}
		
		
		return bean;
	}

	public Map<String, Set<String>> getTravellerCityAndVehicleType(String email) {
		// TODO Auto-generated method stub
	Map<String,Set<String>> traveller=new TreeMap<String,Set<String>>();
		
		Set<String> fromset=new TreeSet<String>();
		Set<String> toset=new TreeSet<String>();
		
		String msg=null;
		PreparedStatement pst=null;

			try {
				String query="SELECT PICK_UP_CITY,DESTINATION_CITY FROM travellers where TRAVELLER_EMAIL='"+email+"' and PICK_UP_CITY is not null and DESTINATION_CITY is not null";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					fromset.add(rs.getString("PICK_UP_CITY"));
					toset.add(rs.getString("DESTINATION_CITY"));
				}
				
				traveller.put("fromCity", fromset);
				traveller.put("toCity", toset);
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
		
		return traveller;
	}

	public List<Travellers> searchTravelerPost(String from, String to, String date,String email) {
		// TODO Auto-generated method stub
//		String date="";
//		if(searchDate!=null && !searchDate.equals("")){
//		date=new SQLDate().getInDate(searchDate);
//		}
		// TODO Auto-generated method stub
		
		String query=null;
		
		if(from==null && to==null && date.equals("")){
			
			query="select * from travellers where TRAVELLER_EMAIL='"+email+"'";
			
		}else if(!from.equals("") && to.equals("") && date.equals("")){
			
			query="select * from travellers where pick_up_city='"+from+"' and TRAVELLER_EMAIL='"+email+"'";
			
		}else if(!from.equals("") && !to.equals("") && date.equals("")){
			
			query="select * from travellers where pick_up_city='"+from+"' and destination_city='"+to+"' and TRAVELLER_EMAIL='"+email+"'";
			
		}else if(!from.equals("") && !to.equals("") && !date.equals("")){
			
			query="select * from travellers where pick_up_city='"+from+"' and destination_city='"+to+"' and traveling_date='"+date+"' and TRAVELLER_EMAIL='"+email+"'";
			
		}else if(!from.equals("") && to.equals("") && !date.equals("")){
			
			query="select * from travellers where pick_up_city='"+from+"' and traveling_date='"+date+"' and TRAVELLER_EMAIL='"+email+"'";
			
		}else if(from.equals("") && !to.equals("") && !date.equals("")){
			
			query="select * from travellers where traveling_date='"+date+"' and destination_city='"+to+"' and TRAVELLER_EMAIL='"+email+"'";
			
		}else if(from.equals("") && to.equals("") && !date.equals("")){
			
			query="select * from travellers where traveling_date='"+date+"' and TRAVELLER_EMAIL='"+email+"'";
			
		}else{
			
			query="select * from travellers where TRAVELLER_EMAIL='"+email+"'";
			
		}
		List<Travellers> traveler=searchTraveller(query);
		return traveler;
	}

	public String updateTravelerStatus(String trId, String email,String status) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;
		try{
			/*set SQL_SAFE_UPDATES=0; */
			String query="update travellers set STATUS='"+status+"' where TRAVELING_ID='"+trId+"' and TRAVELLER_EMAIL='"+email+"'";
			con=DBConnection.getConnection();
			pst=con.prepareStatement(query);
			int i=pst.executeUpdate();
			if(i>0){
				msg="Traveling Inactivated Successfully!.";
			}else{
				msg="SomeThing Went Wrong Please Try Once Again!.";
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

	public Set<String> getTravelerId(String email) {
		// TODO Auto-generated method stub
        Set<String> set=new TreeSet<String>();
		
		PreparedStatement pst=null;

			try {
				String query="SELECT TRAVELER_ID FROM traveler_booking where TRAVELER_EMAIL='"+email+"' and TRAVELER_ID is not null";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					set.add(rs.getString("TRAVELER_ID"));
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
		
		return set;
	}

	public List<TravelerBooking> getTravelerBooking(String email, String trId) {
		// TODO Auto-generated method stub
		List<TravelerBooking> trSet=new ArrayList<TravelerBooking>();
		PreparedStatement pst=null;
		
		String query=null;
		if(trId!=null && !trId.equals("null")){
			query="SELECT * FROM traveler_booking where TRAVELER_EMAIL='"+email+"' and TRAVELER_ID='"+trId+"'";
		}else{
			query="SELECT * FROM traveler_booking where TRAVELER_EMAIL='"+email+"'";
		}
			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					TravelerBooking bean=new TravelerBooking();
					bean.setBOOKING_SEQ_ID(rs.getInt("BOOKING_SEQ_ID"));
					bean.setCAB_ID(rs.getString("CAB_ID"));
					bean.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					bean.setTRAVELER_ID(rs.getString("TRAVELER_ID"));
					bean.setTRAVELER_EMAIL(rs.getString("TRAVELER_EMAIL"));
					bean.setOFFER_PRICE(rs.getString("OFFER_PRICE"));
					bean.setBOOKING_ID(rs.getString("BOOKING_ID"));
					bean.setBOOKING_DATE(new SQLDate().getInDate(rs.getString("BOOKING_DATE")));
					bean.setSTATUS(rs.getString("STATUS"));
					trSet.add(bean);
					
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
		
		return trSet;
	}

	public String travelerCancellationByCabOwner(String bookingId, String feedBack,String status,String postingId,String userId) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;
		try{
			/*set SQL_SAFE_UPDATES=0; */
			String query="update traveler_booking set STATUS='"+status+"' where BOOKING_ID='"+bookingId+"'";
			String query1="update travellers set STATUS='ACTIVE' where TRAVELING_ID='"+postingId+"'";
			String query2="insert into traveler_booking_cancellation values(?,?,?,CURDATE(),?)";
			con=DBConnection.getConnection();
			con.setAutoCommit(false);
			pst=con.prepareStatement(query);
			pst1=con.prepareStatement(query1);
			pst2=con.prepareStatement(query2);
			
			pst2.setInt(1, 0);
			pst2.setString(2, bookingId);
			pst2.setString(3, feedBack);
			pst2.setString(4, userId);
			
			int i=pst.executeUpdate();
			int j=pst1.executeUpdate();
			int k=pst2.executeUpdate();
			if(i>0 && j>0 && k>0){
				con.commit();
				msg="Booking Cancelled Successfully!.";
			}else{
				con.rollback();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();pst1.close();pst2.close();
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return msg;
	}

	public String updateTravelerBookingStatus(String status, String bookingId, String postingId) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		try{
			/*set SQL_SAFE_UPDATES=0; */
			String query="update traveler_booking set STATUS='"+status+"' where BOOKING_ID='"+bookingId+"'";
			String query1="update travellers set STATUS='INACTIVE' where TRAVELING_ID='"+postingId+"'";
			con=DBConnection.getConnection();
			con.setAutoCommit(false);
			pst=con.prepareStatement(query);
			pst1=con.prepareStatement(query1);
			
			int i=pst.executeUpdate();
			int j=pst1.executeUpdate();
			if(i>0 && j>0){
				con.commit();
				
				if(status!=null && status.equals("CONFIRMED")){
					msg="booking confirmed successfully!.";
				}else if(status!=null && status.equals("COMPLETED")){
					msg="traving completed successfully!.";
				}
				
			}else{
				con.rollback();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();pst1.close();
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return msg;
	}


}
