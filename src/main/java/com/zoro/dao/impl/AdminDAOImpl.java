package com.zoro.dao.impl;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.zoro.dao.AdminDAO;
import com.zoro.dto.AddCab;
import com.zoro.dto.AssignDriverCab;
import com.zoro.dto.CabBook;
import com.zoro.dto.CabRoute;
import com.zoro.dto.RegistrationBean;
import com.zoro.dto.TravelerBooking;
import com.zoro.dto.Travellers;
import com.zoro.dto.VehicleTariff;
import com.zoro.dto.VehicleType;
import com.zoro.mailing.utilities.ActivationMailing;
import com.zoro.utilities.DBConnection;
import com.zoro.utilities.SQLDate;

@Component
public class AdminDAOImpl implements AdminDAO {
	
	private   Connection con=null;
	private int noOfRecords=0;

	public List<RegistrationBean> getRegisteredDetails(String userType,String userId) {
		List<RegistrationBean> regList=new ArrayList<RegistrationBean>();
		
		PreparedStatement pst=null;
		String query=null;
		if(userId!=null && !userId.equals("null")){
			query="select * from registration where USER_TYPE='"+userType+"' and EMAIL_ID='"+userId+"'";
		}else{
			query="select * from registration where USER_TYPE='"+userType+"'";
		}

			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					RegistrationBean reg=new RegistrationBean();
					
					reg.setREG_ID(rs.getInt("REG_ID"));
					reg.setCANDIDATE_NAME(rs.getString("CANDIDATE_NAME"));
					reg.setADDRESS(rs.getString("ADDRESS"));
					reg.setEMAIL_ID(rs.getString("EMAIL_ID"));
					reg.setCONTACT_NO(rs.getString("CONTACT_NO"));
					reg.setUSER_TYPE(rs.getString("USER_TYPE"));
					reg.setSTATUS(rs.getString("STATUS"));
					//reg.setREG_DATE(new SQLDate().getInDate(rs.getString("REG_DATE")));
					reg.setCANDIDATE_ID(rs.getString("CANDIDATE_ID"));
					regList.add(reg);
					
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
		
		return regList;
	}

	public Set<String> getUserId(String userType) {
		Set<String> userTypeSet=new TreeSet<String>();
		
		PreparedStatement pst=null;

			try {
				String query="select EMAIL_ID from registration where USER_TYPE='"+userType+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
				userTypeSet.add(rs.getString("EMAIL_ID"));
					
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
		
		return userTypeSet;
	}

	public String changeStatus(String status, String email) {
		String msg=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;

			try {
				String query="update registration set STATUS='"+status+"' where  EMAIL_ID='"+email+"'";
				String query1="update login set STATUS='"+status+"' where  EMAIL_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			    pst1=con.prepareStatement(query1);
			    
			    int i=pst.executeUpdate();
			    int j=pst1.executeUpdate();
			
			if(i>0 && j>0){
				if(status!=null && status.equals("ACTIVE")){
					new ActivationMailing().activationOfRegUserByAdmin(email, "Activated");
				msg=email+" Activated Successfully!.";	
				}else if(status!=null && status.equals("INACTIVE")){
					new ActivationMailing().activationOfRegUserByAdmin(email, "Inactivated");
					msg=email+" Inactivated Successfully!.";
				}else if(status!=null && status.equals("BLOCK")){
					new ActivationMailing().activationOfRegUserByAdmin(email, "Blocked");
					msg=email+" Blocked Successfully!.";
				}
			}else{
				msg="SomeThing Went Wrong Please Try Once Again!.";
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

	public String addVehicleType(String brand,String model, String email,String noOfPas, InputStream image,String bodyType) {
		String msg=null;
		PreparedStatement pst=null;

			try {
				String query="insert into vehicle_type values(?,?,?,?,?,?,?)";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			    
			    pst.setInt(1, 0);
			    pst.setString(2, brand);
			    pst.setBlob(3, image);
			    pst.setString(4, email);
			    pst.setString(5, noOfPas);
			    pst.setString(6, model);
			    pst.setString(7, bodyType);
			    int i=pst.executeUpdate();
				
			    if(i>0){
			    	msg="vehicle type added successfully!.";
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

	public List<VehicleType> searchVehicleType(String email) {
		// TODO Auto-generated method stub
		List<VehicleType> vehicleList=new ArrayList<VehicleType>();
		
		PreparedStatement pst=null;

		
			try {
				String query="select * from vehicle_type where ADMIN_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				byte photo[];
				Blob blob;
				int i=1;
				while(rs.next()){
					
					VehicleType vehicle=new VehicleType();
					vehicle.setVEHICLE_SEQ_NO(rs.getInt("VEHICLE_SEQ_NO"));
					vehicle.setBRAND(rs.getString("BRAND"));
					vehicle.setMODEL(rs.getString("MODEL"));
					vehicle.setADMIN_ID(rs.getString("ADMIN_ID"));
					vehicle.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					vehicle.setBODY_TYPE(rs.getString("BODY_TYPE"));
					
					blob=rs.getBlob("IMAGE");
					photo=blob.getBytes(1, (int)blob.length());
					String photo1=Base64.getEncoder().encodeToString(photo);
					vehicle.setIMAGE(photo1);
					
					vehicleList.add(vehicle);
					
					i++;
					
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
		
		return vehicleList;
	}

	public Set<String> getVehicleType() {
		// TODO Auto-generated method stub
	Set<String> vehicleSet=new TreeSet<String>();
		
		PreparedStatement pst=null;

		
			try {
				String query="select * from vehicle_type";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					vehicleSet.add(rs.getString("BRAND"));
					
					
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
		
		return vehicleSet;
	}


	public List<VehicleType> getAdminVehicleType() {
		// TODO Auto-generated method stub
		List<VehicleType> vehicleList=new ArrayList<VehicleType>();
		PreparedStatement pst=null;

		
			try {
				String query="select * from vehicle_type";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				byte photo[];
				Blob blob;
				while(rs.next()){
					
					VehicleType vehicle=new VehicleType();
					
					vehicle.setBRAND(rs.getString("BRAND"));
					vehicle.setMODEL(rs.getString("MODEL"));
					vehicle.setBODY_TYPE(rs.getString("BODY_TYPE"));
					vehicle.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					blob=rs.getBlob("IMAGE");
					photo=blob.getBytes(1, (int)blob.length());
					String photo1=Base64.getEncoder().encodeToString(photo);
					
					vehicle.setIMAGE(photo1);
					
					vehicleList.add(vehicle);
					
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
		
		return vehicleList;
	}

	public Map<String, Set<String>> getAdCabModel(String brand) {
		// TODO Auto-generated method stub
		Map<String,Set<String>> map=new TreeMap<String,Set<String>>();
		
		Set<String> modelset=new TreeSet<String>();
		Set<String> noOfPasset=new TreeSet<String>();
		Set<String> bodyTypeSet=new TreeSet<String>();
		
		PreparedStatement pst=null;

			try {
				String query="SELECT * FROM vehicle_type where BRAND='"+brand+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					modelset.add(rs.getString("MODEL"));
					noOfPasset.add(rs.getString("NO_OF_PASSENGER"));
					bodyTypeSet.add(rs.getString("BODY_TYPE"));
					
				}
				map.put("model", modelset);
				map.put("noOfPas", noOfPasset);
				map.put("bodyType", bodyTypeSet);
				
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
	
	public Set<String> getNoOfPassenger(String model) {
		// TODO Auto-generated method stub
Set<String> noOfPassenger=new TreeSet<String>();
		
		PreparedStatement pst=null;

			try {
				String query="SELECT NO_OF_PASSENGER FROM vehicle_type where MODEL='"+model+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					noOfPassenger.add(rs.getString("NO_OF_PASSENGER"));
					
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
		
		return noOfPassenger;
	}
	
	public Set<String> getVehicleModel() {
		// TODO Auto-generated method stub
	Set<String> vehicleSet=new TreeSet<String>();
		
		PreparedStatement pst=null;

		
			try {
				String query="select MODEL from vehicle_type";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					vehicleSet.add(rs.getString("MODEL"));
					
					
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
		
		return vehicleSet;

}

	public List<AddCab> getAdminCab(String email) {
		// TODO Auto-generated method stub
		List<AddCab> cabList=new ArrayList<AddCab>();
		PreparedStatement pst=null;

		
			try {
				String query="select * from add_cab where CAB_OWNER_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					AddCab cab=new AddCab();
					cab.setCAB_REG_NO(rs.getString("CAB_REG_NO"));
					cab.setCAB_GEN_ID(rs.getString("CAB_GEN_ID"));
					cab.setCAB_BRAND(rs.getString("CAB_BRAND"));
					cab.setCAB_MODEL(rs.getString("CAB_MODEL"));
					cab.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					cab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					cabList.add(cab);
				
					
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
		
		return cabList;
	}

	public AddCab viewAdCab(String email, String cabId) {
		// TODO Auto-generated method stub
		PreparedStatement pst=null;
		
		AddCab addCab=new AddCab();
		Blob blob;
		Blob blob1;
		Blob blob2;
		byte[] image;
		byte[] rcDoc;
		byte[] insDoc;
			try {
				String query="SELECT * FROM add_cab where CAB_REG_NO='"+cabId+"' and CAB_OWNER_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
					addCab.setCAB_GEN_ID(rs.getString("CAB_GEN_ID"));
					addCab.setCAB_REG_NO(rs.getString("CAB_REG_NO"));
					addCab.setCAB_BRAND(rs.getString("CAB_BRAND"));
					addCab.setCAB_MODEL(rs.getString("CAB_MODEL"));
					addCab.setADDRESS(rs.getString("ADDRESS"));
					addCab.setBODY_TYPE(rs.getString("BODY_TYPE"));
					addCab.setCAB_SEQ_ID(rs.getInt("CAB_SEQ_ID"));
					addCab.setCERTIFIED_COMP_NAME(rs.getString("CERTIFIED_COMP_NAME"));
					addCab.setCITY(rs.getString("CITY"));
					addCab.setCOLOR(rs.getString("COLOR"));
					addCab.setCURRENT_MILEAGE(rs.getString("CURRENT_MILEAGE"));
					addCab.setDISTRICT(rs.getString("DISTRICT"));
					addCab.setFUEL_TYPE(rs.getString("FUEL_TYPE"));
					addCab.setINSURENCE_COMP_NAME(rs.getString("INSURENCE_COMP_NAME"));
					addCab.setKM_DRIVEN(rs.getString("KM_DRIVEN"));
					addCab.setMOBILE_NO(rs.getString("MOBILE_NO"));
					addCab.setMODEL_YEAR(rs.getString("MODEL_YEAR"));
					addCab.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					addCab.setPINCODE(rs.getString("PINCODE"));
					addCab.setREGISTERED_CITY(rs.getString("REGISTERED_CITY"));
					addCab.setREGISTERED_STATE(rs.getString("REGISTERED_STATE"));
					addCab.setREGISTERED_YEAR(rs.getString("REGISTERED_YEAR"));
					addCab.setSTATE(rs.getString("STATE"));
					addCab.setSTATUS(rs.getString("STATUS"));
					addCab.setTRANSMISSION(rs.getString("TRANSMISSION"));
					
					blob=rs.getBlob("CAB_PHOTO");
					blob1=rs.getBlob("RC_DOC");
					blob2=rs.getBlob("INSURANCE_DOC");
					
					image=blob.getBytes(1, (int)blob.length());
					rcDoc=blob1.getBytes(1, (int)blob1.length());
					insDoc=blob2.getBytes(1, (int)blob2.length());
					
					String photo=Base64.getEncoder().encodeToString(image);
					String rcDocument=Base64.getEncoder().encodeToString(rcDoc);
					String insuranceDoc=Base64.getEncoder().encodeToString(insDoc);
					
					addCab.setCAB_PHOTO(photo);
					addCab.setRC_DOC(rcDocument);
					addCab.setINSURANCE_DOC(insuranceDoc);
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
		
		return addCab;
	}

	public List<CabRoute> viewAdCabRout(String email, String cabId) {
		// TODO Auto-generated method stub
		List<CabRoute> routeList=new ArrayList<CabRoute>();
		PreparedStatement pst=null;

		String query=null;
		if(cabId!=null){
			query="select * from cab_route where CAB_OWNER_ID='"+email+"' and CAB_ID='"+cabId+"'";	
		}else{
			query="select * from cab_route where CAB_OWNER_ID='"+email+"'";
		}
		
			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				byte photo[];
				Blob blob;
				while(rs.next()){
					
					CabRoute route=new CabRoute();
					
					route.setCAB_MODEL(rs.getString("CAB_MODEL"));
					route.setSOURCE_ADDRESS(rs.getString("SOURCE_ADDRESS"));
					route.setDESTINATION(rs.getString("DESTINATION"));
					route.setPICK_UP_DATE(rs.getString("PICK_UP_DATE"));
					route.setFROM_LOCATION(rs.getString("FROM_LOCATION"));
					route.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					route.setPRICE(rs.getString("PRICE"));
					route.setPRICE_PER_KM(rs.getString("PRICE_PER_KM"));
					route.setCAB_ID(rs.getString("CAB_ID"));
					route.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					route.setAVAILABILITY(rs.getString("AVAILABILITY"));
					route.setSTATUS(rs.getString("STATUS"));
				
					routeList.add(route);
				
					
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
		
		return routeList;
	}
	
	public List<AddCab> getCabDetails(String query) {
		// TODO Auto-generated method stub
		List<AddCab> cabList=new ArrayList<AddCab>();
		PreparedStatement pst=null;

		
			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					AddCab cab=new AddCab();
					cab.setCAB_REG_NO(rs.getString("CAB_REG_NO"));
					cab.setCAB_GEN_ID(rs.getString("CAB_GEN_ID"));
					cab.setCAB_BRAND(rs.getString("CAB_BRAND"));
					cab.setCAB_MODEL(rs.getString("CAB_MODEL"));
					cab.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					cab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					cab.setSTATUS(rs.getString("STATUS"));
					cabList.add(cab);
				
					
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
		
		return cabList;
	}

	public List<AddCab> getAdminCab(String email, String cabId) {
		// TODO Auto-generated method stub
		String query="";
		
		if(cabId!=null && !cabId.equals("") && !cabId.equals("All")){
			query="SELECT * FROM add_cab where CAB_REG_NO='"+cabId+"' and CAB_OWNER_ID='"+email+"'";
		}else if(cabId==null||cabId.equals("")||cabId.equals("All")){
			query="SELECT * FROM add_cab where CAB_OWNER_ID='"+email+"'";
		}
		List<AddCab> cabList=getCabDetails(query);
		
		return cabList;
	}

	public List<CabBook> getCabBookingDetails(String query) {
		// TODO Auto-generated method stub
		List<CabBook> bookList=new ArrayList<CabBook>();
		PreparedStatement pst=null;

			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					CabBook cab=new CabBook();
					
					cab.setBOOKING_ID(rs.getString("BOOKING_ID"));
					cab.setCONTACT_NO(rs.getString("CONTACT_NO"));
					cab.setPICK_UP_ADDRESS(rs.getString("PICK_UP_ADDRESS"));
					cab.setBOOKING_STATUS(rs.getString("BOOKING_STATUS"));
					cab.setBOOKING_DATE(rs.getString("BOOKING_DATE"));
					cab.setCAB_ID(rs.getString("CAB_ID"));
					cab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					cab.setUSER_EMAIL(rs.getString("USER_EMAIL"));
					bookList.add(cab);
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
		
		return bookList;
	}

	public List<CabBook> serachCabBooking(String cabId, String email, String date1,String date2) {
		// TODO Auto-generated method stub
		String query="";
	
		
		if(date1!=null && !date1.equals("") && !date2.equals("")){
			
			query="SELECT * FROM cab_booking where CAB_OWNER_ID='"+email+"' and CAB_ID='"+cabId+"' and BOOKING_DATE between '"+date1+"' and '"+date2+"'";
		
		}else if(date1!=null && !date1.equals("")){
			
			query="SELECT * FROM cab_booking where CAB_OWNER_ID='"+email+"' and CAB_ID='"+cabId+"' and BOOKING_DATE ='"+date1+"'";

		}else if(date2!=null && !date2.equals("")){
			
			query="SELECT * FROM cab_booking where CAB_OWNER_ID='"+email+"' and CAB_ID='"+cabId+"' and BOOKING_DATE ='"+date2+"'";

		}else{
			
			query="SELECT * FROM cab_booking where CAB_OWNER_ID='"+email+"' and CAB_ID='"+cabId+"'";

		}
		
		
		List<CabBook> cabBookingList=getCabBookingDetails(query);
		
		return cabBookingList;
	}

	public String getCabBookingCount(String cabId, String ownerId) {
		// TODO Auto-generated method stub
		String count="";
		PreparedStatement pst=null;

		
			try {
				String query="select count(*) from cab_booking where CAB_ID='"+cabId+"' and CAB_OWNER_ID='"+ownerId+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
			    count=rs.getString("count(*)");
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
		
		return count;
	}

	public String getCandidatename(String email) {
		// TODO Auto-generated method stub
		String name="";
		PreparedStatement pst=null;

		
			try {
				String query="select CANDIDATE_NAME from registration where EMAIL_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
			    name=rs.getString("CANDIDATE_NAME");
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
		
		return name;
	}

	public List<AssignDriverCab> viewCabDriver(String email, String driverId) {
		List<AssignDriverCab> assignList=new ArrayList<AssignDriverCab>();
		PreparedStatement pst=null;

			try {
				String query="SELECT * FROM assign_driver_cab where CAB_OWNER_ID='"+email+"' and DRIVER_ID='"+driverId+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					AssignDriverCab assignDriver=new AssignDriverCab();
					
					assignDriver.setASSIGN_ID(rs.getInt("ASSIGN_ID"));
					assignDriver.setASSIGN_DATE(rs.getString("ASSIGN_DATE"));
					assignDriver.setASSIGN_TIME(rs.getString("ASSIGN_TIME"));
					assignDriver.setCAB_ID(rs.getString("CAB_ID"));
					assignDriver.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					assignDriver.setDRIVER_ID(rs.getString("DRIVER_ID"));
					assignDriver.setSTATUS(rs.getString("STATUS"));
					
					assignList.add(assignDriver);
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
		
		return assignList;
	}

	public List<Travellers> searchAdTravelerPost(String email,String trId) {
		// TODO Auto-generated method stub
		String query="";
		
		if(trId!=null && !trId.equals("") && !trId.equals("All")){
			
			query="select * from travellers where TRAVELLER_EMAIL='"+email+"' and TRAVELING_ID='"+trId+"'";
		}else{
			query="select * from travellers where TRAVELLER_EMAIL='"+email+"'";
		}
		List<Travellers> postList=new TravellerDAOImpl().searchTraveller(query);
		return postList;
	}

	public Set<String> getTravelerPostingId(String email) {
		// TODO Auto-generated method stub
		Set<String> trList=new TreeSet<String>();
		PreparedStatement pst=null;

			try {
				String query="SELECT TRAVELING_ID FROM travellers where TRAVELLER_EMAIL='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
				trList.add(rs.getString("TRAVELING_ID"));
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
		
		return trList;
	}

	public String getTravelerBookingCount(String email, String trId) {
		// TODO Auto-generated method stub
		String count="";
		PreparedStatement pst=null;

		
			try {
				String query="select count(*) from traveler_booking where TRAVELER_ID='"+trId+"' and TRAVELER_EMAIL='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
			    count=rs.getString("count(*)");
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
		
		return count;
	}

	public List<TravelerBooking> searchTravellerBooking(String email, String trId, String date1, String date2) {
		// TODO Auto-generated method stub
		
		String query="";
		
		if(date1!=null && !date1.equals("")){
			date1=new SQLDate().getSQLDate(date1);
		}if(date2!=null && !date2.equals("")){
			date2=new SQLDate().getSQLDate(date2);
		}
		
	if(date1!=null && !date1.equals("") && !date2.equals("")){
			
			query="SELECT * FROM traveler_booking where TRAVELER_EMAIL='"+email+"' and TRAVELER_ID='"+trId+"' and BOOKING_DATE between '"+date1+"' and '"+date2+"'";
		
		}else if(date1!=null && !date1.equals("")){
			
			query="SELECT * FROM traveler_booking where TRAVELER_EMAIL='"+email+"' and TRAVELER_ID='"+trId+"' and BOOKING_DATE ='"+date1+"'";

		}else if(date2!=null && !date2.equals("")){
			
			query="SELECT * FROM traveler_booking where TRAVELER_EMAIL='"+email+"' and TRAVELER_ID='"+trId+"' and BOOKING_DATE ='"+date2+"'";

		}else{
			
			query="SELECT * FROM traveler_booking where TRAVELER_EMAIL='"+email+"' and TRAVELER_ID='"+trId+"'";

		}
	
	List<TravelerBooking> bookList=getTravelerBookingDetails(query);
	
	return bookList;
	
	}

	public List<TravelerBooking> getTravelerBookingDetails(String query) {
		// TODO Auto-generated method stub
		List<TravelerBooking> bookList=new ArrayList<TravelerBooking>();
		PreparedStatement pst=null;

			try {
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					TravelerBooking traveler=new TravelerBooking();
					
					traveler.setBOOKING_SEQ_ID(rs.getInt("BOOKING_SEQ_ID"));
					traveler.setBOOKING_ID(rs.getString("BOOKING_ID"));
					traveler.setCAB_ID(rs.getString("CAB_ID"));
					traveler.setBOOKING_DATE(rs.getString("BOOKING_DATE"));
					traveler.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					traveler.setOFFER_PRICE(rs.getString("OFFER_PRICE"));
					traveler.setSTATUS(rs.getString("STATUS"));
					traveler.setTRAVELER_EMAIL(rs.getString("TRAVELER_EMAIL"));
					traveler.setTRAVELER_ID(rs.getString("TRAVELER_ID"));
				
					bookList.add(traveler);
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
		
		return bookList;
	}

	public String InsertCabFeedBack(String cabId, String status, String email,String feedBack) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;

			try {
				String defaultPlanDuration="180 days";
				String expiryDate=getExpiryDate(cabId);
				int seqId=new CabDAOImpl().getCabSEQID(cabId);
//				String defaultPlanId="114";
				String query="insert into cab_verification_feedback values(?,?,?,?)";
//				String query1="update add_cab set STATUS='"+status+"' where CAB_REG_NO='"+cabId+"'";
				String query1="update add_cab set STATUS='"+status+"',CAB_VALID_DAYS='"+defaultPlanDuration+"',CAB_EXPIRY_DATE='"+expiryDate+"' where CAB_REG_NO='"+cabId+"'";  //change by arti

				String query2 ="insert into cab_membership_request_details(plan_duration,plan_expiry_date,membership_plan_id,membership_request_id,add_cab_id) values(?,?,?,?,?)"; //Code changes By danish
				
				
				con=DBConnection.getConnection();
				con.setAutoCommit(false);
			    pst=con.prepareStatement(query);
			    pst1=con.prepareStatement(query1);
			    pst2=con.prepareStatement(query2);
			    
			    pst.setInt(1, 0);
			    pst.setString(2, cabId);
			    pst.setString(3, feedBack);
			    pst.setString(4, email);
			    
				pst2.setString(1, defaultPlanDuration);
				pst2.setString(2, expiryDate);
				pst2.setInt(3, 0);
				pst2.setLong(4, 0);
				pst2.setInt(5, seqId);
				
						    
			    int i=pst.executeUpdate();
			    int j=pst1.executeUpdate();
			    int k=pst2.executeUpdate();	
			    
			    if(i==0 && j==0 && k==0){
			    	
			    	con.rollback();			    	
			    }
				
			    if(i>0 && j>0 && k>0){
			    	con.commit();
			    	msg="FeedBack Added Successfully and verification mail has been sent to cab owner!.";
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

	public String getInactiveCabCount() {
		// TODO Auto-generated method stub
		String count=null;
		PreparedStatement pst=null;

			try {
				String query="SELECT count(*) FROM add_cab where STATUS='INACTIVE'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
				count=rs.getString("count(*)");
					
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
		
		return count;
	}
	
	public String getCabRequest(String email) {
		// TODO Auto-generated method stub
		String count=null;
		PreparedStatement pst=null;

			try {
				String query="SELECT count(*) FROM cab_booking where BOOKING_STATUS='BOOKED' and CAB_OWNER_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
				count=rs.getString("count(*)");
//					System.out.println(count);
				}
//				System.out.println(query);
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
		
		return count;
	}

	public String insertEmpRole(String empId, String empRole) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;

			try {
				String query="insert into emp_role values(?,?,?,CURDATE())";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query);
			    
			    pst.setInt(1, 0);
			    pst.setString(2, empId);
			    pst.setString(3, empRole);
			    
			    int i=pst.executeUpdate();
				
			    if(i>0){
			    	msg="Employee Assigned successfully!.";
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

	public String getEmpRole(String email) {
		// TODO Auto-generated method stub
		String empRole=null;
		PreparedStatement pst=null;

			try {
				String query="SELECT ROLE from emp_role where EMP_ID='"+email+"'";
				con=DBConnection.getConnection();
			    pst=con.prepareStatement(query,pst.RETURN_GENERATED_KEYS);
			
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					
				empRole=rs.getString("ROLE");
					
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
		
		return empRole;
	}

	public String updateRole(String empId, String role) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;

			try {
				String query="update emp_role set ROLE='"+role+"' where EMP_ID='"+empId+"'";
				con=DBConnection.getConnection();
				con.setAutoCommit(false);
			    pst=con.prepareStatement(query);
			    
			    int i=pst.executeUpdate();
			    
			    if(i==0){
			    	
			    	con.rollback();
			    }
				
			    if(i>0){
			    	con.commit();
			    	msg="Employee Role Updated Successfully!.";
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

	public String updateVehicleType(VehicleType vehicle, InputStream image) {
		// TODO Auto-generated method stub
		String msg=null;
		PreparedStatement pst=null;
		
			try {
				String query="update vehicle_type set BRAND=?,IMAGE=?,ADMIN_ID=?,NO_OF_PASSENGER=?,MODEL=?,BODY_TYPE=? where VEHICLE_SEQ_NO='"+vehicle.getVEHICLE_SEQ_NO()+"'";
				con=DBConnection.getConnection();
				con.setAutoCommit(false);
			    pst=con.prepareStatement(query);
			    
			    pst.setString(1, vehicle.getBRAND());
			    pst.setBlob(2, image);
			    pst.setString(3, vehicle.getADMIN_ID());
			    pst.setString(4, vehicle.getNO_OF_PASSENGER());
			    pst.setString(5, vehicle.getMODEL());
			    pst.setString(6, vehicle.getBODY_TYPE());
			    
			    int i=pst.executeUpdate();
			    
			    if(i==0){
			    	
			    	con.rollback();
			    }
				
			    if(i>0){
			    	con.commit();
			    	msg="Vehicle Type Updated Successfully!.";
			    }
			   //System.out.println(query); 
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

	@Override
	public String addTariff(VehicleTariff vt) {
		String msg=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;
		String vehicleDetailsQuery="INSERT INTO `vehicle_price_details` (`BODY_TYPE`, `BASIC_PRICE`, `PRICE_PER_KM`, `PRICE_PER_8HOURS`, `PRICE_PER_HOUR`, `PRICE_PER_DAY`, `WAITING_CHARGES`, `DISCOUNT`, `SERVICE_TYPE`, `CITY`, `STATE`, `DATE_UPDATED`, `EMPTY6`, `EMPTY7`, `EMPTY8`, `EMPTY9`, `EMPTY10`, `UPDATED_BY`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate(), ?, ?, ?, ?, ?, ?)";
		String vehicleHeaderQuery="INSERT INTO `vehicle_price_header` (`BODY_TYPE`, `BASIC_PRICE`, `PRICE_PER_KM`, `PRICE_PER_8HOURS`, `PRICE_PER_HOUR`, `PRICE_PER_DAY`, `WAITING_CHARGES`, `DISCOUNT`, `SERVICE_TYPE`, `CITY`, `STATE`, `DATE_UPDATED`, `EMPTY6`, `EMPTY7`, `EMPTY8`, `EMPTY9`, `EMPTY10`, `UPDATED_BY`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate(), ?, ?, ?, ?, ?, ?)";
		String vehicleHeaderUpdateQuery="UPDATE `vehicle_price_header` SET `BODY_TYPE`=?, `BASIC_PRICE`=?, `PRICE_PER_KM`=?, `PRICE_PER_8HOURS`=?, `PRICE_PER_HOUR`=?,"
				+ " `PRICE_PER_DAY`=?, `WAITING_CHARGES`=?, `DISCOUNT`=?, `SERVICE_TYPE`=?, "
				+ "`CITY`=?, `STATE`=?, `DATE_UPDATED`=sysdate(), `EMPTY6`=?, `EMPTY7`=?, `EMPTY8`=?, `EMPTY9`=?, `EMPTY10`=?, `UPDATED_BY`=? WHERE `SNO`=?";
			try {
				VehicleTariff vvt = checkTariff(vt);
				con=DBConnection.getConnection();
				con.setAutoCommit(false);
				if(vvt!=null)
				{
					
					    pst2=con.prepareStatement(vehicleHeaderUpdateQuery);
						pst2.setString(1, vt.getBODY_TYPE());
					    pst2.setString(2, vt.getBASIC_PRICE());
					    pst2.setString(3, vt.getPRICE_PER_KM());
					    pst2.setString(4, vt.getPRICE_PER_8HOURS());
					    pst2.setString(5, vt.getPRICE_PER_HOUR());
					    pst2.setString(6, vt.getPRICE_PER_DAY());
					    pst2.setString(7, vt.getWAITING_CHARGES());
					    pst2.setString(8, vt.getDISCOUNT());
					    pst2.setString(9, vt.getSERVICE_TYPE());
					    pst2.setString(10, vt.getCITY());
					    pst2.setString(11, vt.getSTATE());
					    pst2.setString(12, "");
					    pst2.setString(13, "");
					    pst2.setString(14, "");
					    pst2.setString(15, "");
					    pst2.setString(16, "");
					    pst2.setString(17, vt.getUPDATED_BY());
					    pst2.setInt(18, vvt.getSNO());
					
					    pst2.executeUpdate();
				}
				else
				{
					pst1=con.prepareStatement(vehicleHeaderQuery);
			    pst1.setString(1, vt.getBODY_TYPE());
			    pst1.setString(2, vt.getBASIC_PRICE());
			    pst1.setString(3, vt.getPRICE_PER_KM());
			    pst1.setString(4, vt.getPRICE_PER_8HOURS());
			    pst1.setString(5, vt.getPRICE_PER_HOUR());
			    pst1.setString(6, vt.getPRICE_PER_DAY());
			    pst1.setString(7, vt.getWAITING_CHARGES());
			    pst1.setString(8, vt.getDISCOUNT());
			    pst1.setString(9, vt.getSERVICE_TYPE());
			    pst1.setString(10, vt.getCITY());
			    pst1.setString(11, vt.getSTATE());
			    pst1.setString(12, "");
			    pst1.setString(13, "");
			    pst1.setString(14, "");
			    pst1.setString(15, "");
			    pst1.setString(16, "");
			    pst1.setString(17, vt.getUPDATED_BY());
			    pst1.executeUpdate();
				}
				pst=con.prepareStatement(vehicleDetailsQuery);
			    
			    pst.setString(1, vt.getBODY_TYPE());
			    pst.setString(2, vt.getBASIC_PRICE());
			    pst.setString(3, vt.getPRICE_PER_KM());
			    pst.setString(4, vt.getPRICE_PER_8HOURS());
			    pst.setString(5, vt.getPRICE_PER_HOUR());
			    pst.setString(6, vt.getPRICE_PER_DAY());
			    pst.setString(7, vt.getWAITING_CHARGES());
			    pst.setString(8, vt.getDISCOUNT());
			    pst.setString(9, vt.getSERVICE_TYPE());
			    pst.setString(10, vt.getCITY());
			    pst.setString(11, vt.getSTATE());
			    pst.setString(12, "");
			    pst.setString(13, "");
			    pst.setString(14, "");
			    pst.setString(15, "");
			    pst.setString(16, "");
			    pst.setString(17, vt.getUPDATED_BY());
			    
			    int i=pst.executeUpdate();
			    
			    if(i==0){
			    	
			    	con.rollback();
			    }
				
			    if(i>0){
			    	con.commit();
			    	msg="Vehicle Tariff added Successfully!.";
			    }
			    else
			    {
			    	msg="Vehicle Tariff add Failed. Please try again!";
			    }
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		return msg;
	}

	@Override
	public VehicleTariff checkTariff(VehicleTariff vehicleTariff) {
				String query="SELECT * FROM vehicle_price_header where BODY_TYPE = '"+vehicleTariff.getBODY_TYPE()+"' and SERVICE_TYPE = '"+vehicleTariff.getSERVICE_TYPE()+"' and city = '"+vehicleTariff.getCITY()+"' and state = '"+vehicleTariff.getSTATE()+"'";
				ArrayList<VehicleTariff> alvt=new ArrayList<VehicleTariff>();
				VehicleTariff vt=new VehicleTariff();
				alvt=getVehicleTariffDetails(query);
				if(alvt.size()>0)
				{
					vt=alvt.get(0);
				}
				else
				{
					vt=null;
				}
		return vt;
	}

	@Override
	public ArrayList<VehicleTariff> getAllTariff(VehicleTariff vt,int offset,int noOfRecords) {
		ArrayList<VehicleTariff> alvt=new ArrayList<VehicleTariff>();
				String query="SELECT SQL_CALC_FOUND_ROWS * FROM vehicle_price_header where UPDATED_BY is not null";
				
				if(vt.getBODY_TYPE()!=null)
				{
					query+=" and BODY_TYPE = '"+vt.getBODY_TYPE()+"'";
				}
				
				if(vt.getSERVICE_TYPE()!=null)
				{
					query+=" and SERVICE_TYPE = '"+vt.getSERVICE_TYPE()+"'";
				}
				
				if(vt.getCITY()!=null)
				{
					query+=" and CITY = '"+vt.getCITY()+"'";
				}
				query+=" limit " + offset + ", " + noOfRecords;
				System.out.println(query);
		alvt=getVehicleTariffDetails(query);		
		return alvt;
	}

	@Override
	public VehicleTariff getTariffDetails(VehicleTariff vehicleTariff) {
				String query="SELECT * FROM vehicle_price_details where UPDATED_BY is not null";
				
				if(vehicleTariff.getSNO()!=0)
				{
					query+=" and SNO = '"+vehicleTariff.getSNO()+"'" ;
				}
				
				ArrayList<VehicleTariff> alvt=new ArrayList<VehicleTariff>();
				VehicleTariff vt=new VehicleTariff();
				alvt=getVehicleTariffDetails(query);
				if(alvt.size()>0)
				{
					vt=alvt.get(0);
				}
		return vt;
	}
	
	private ArrayList<VehicleTariff> getVehicleTariffDetails(String query)
	{
		ArrayList<VehicleTariff> alvt=new ArrayList<VehicleTariff>();
		Statement st=null;
		ResultSet rs=null;
			try {
				con=DBConnection.getConnection();
				st=con.createStatement();
				rs=st.executeQuery(query);
				while(rs.next())
				{
					VehicleTariff vt=new VehicleTariff();
					vt.setSNO(rs.getInt("SNO"));
					vt.setBODY_TYPE(rs.getString("BODY_TYPE"));
					vt.setBASIC_PRICE(rs.getString("BASIC_PRICE"));
					vt.setPRICE_PER_KM(rs.getString("PRICE_PER_KM"));
					vt.setPRICE_PER_HOUR(rs.getString("PRICE_PER_HOUR"));
					vt.setPRICE_PER_DAY(rs.getString("PRICE_PER_DAY"));
					vt.setPRICE_PER_8HOURS(rs.getString("PRICE_PER_8HOURS"));
					vt.setWAITING_CHARGES(rs.getString("WAITING_CHARGES"));
					vt.setUPDATED_BY(rs.getString("UPDATED_BY"));
					vt.setDISCOUNT(rs.getString("DISCOUNT"));
					vt.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));
					vt.setCITY(rs.getString("CITY"));
					vt.setSTATE(rs.getString("STATE"));
					vt.setDATE_UPDATED(rs.getString("DATE_UPDATED"));
					alvt.add(vt);
				}
				rs.close();
				rs = st.executeQuery("SELECT FOUND_ROWS()");
				if(rs.next())
					this.noOfRecords = rs.getInt(1);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		return alvt;
	}

	@Override
	public boolean updateTariffDetails(VehicleTariff vehicleTariff) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getNoOfRecords()
	{
		return noOfRecords;
	}

	@Override
	public ArrayList<VehicleTariff> searchVehicleTariff(VehicleTariff vt,int limit,int offset) {
		// TODO Auto-generated method stub
		ArrayList<VehicleTariff> alvt = new ArrayList<VehicleTariff>();
		String query="SELECT * FROM vehicle_price_header where updated_by is not null city=''";
		
		if(vt.getSERVICE_TYPE()!=null)
		{
			query+=" and SERVICE_TYPE = '"+vt.getSERVICE_TYPE()+"'";
		}
		
		if(vt.getCITY()!=null)
		{
			query+=" and CITY = '"+vt.getCITY()+"'";
		}
		
		query+="";
		
		alvt=getVehicleTariffDetails(query);
		return alvt;
	}

	@Override
	public ArrayList<VehicleTariff> getAllTariff(VehicleTariff vt) {
		ArrayList<VehicleTariff> alvt=new ArrayList<VehicleTariff>();
		String query="SELECT SQL_CALC_FOUND_ROWS * FROM vehicle_price_header where UPDATED_BY is not null";
		
		if(vt.getBODY_TYPE()!=null)
		{
			if(vt.getBODY_TYPE()!="")
			{
			query+=" and BODY_TYPE = '"+vt.getBODY_TYPE()+"'";
			}
		}
		
		if(vt.getSERVICE_TYPE()!=null)
		{
			if(vt.getSERVICE_TYPE()!="")
			{
			query+=" and SERVICE_TYPE = '"+vt.getSERVICE_TYPE()+"'";
			}
		}
		
		if(vt.getCITY()!=null)
		{
			if(vt.getCITY()!="")
			{
			query+=" and CITY = '"+vt.getCITY()+"'";
			}
		}
		System.out.println(query);
		alvt=getVehicleTariffDetails(query);		
		return alvt;
	}
	
	// added by arti
		private String getExpiryDate(String cabRegNo) throws ParseException {
			String days = "180 days";
			String cabAddedDate=new CabDAOImpl().getCabAddedDateByCabRegNo(cabRegNo);
			
//			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MMM-dd");	  
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");	  
		    Date date = inputFormat.parse(cabAddedDate);
		     		
		//	Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = formatter.format(date);
			
			System.out.println("strDate "+strDate);			
			int days1=Integer.parseInt(days.replace(" days", ""));		
			String[] split1 = strDate.split("/");
			String year1 = "";
			String month1 = "";
			String day1 = "";
			if (split1.length == 3)
				day1 = split1[0];
			if (split1.length == 3)
				month1 = split1[1];
			if (split1.length == 3)
				year1 = split1[2];
//			int d1 = Integer.parseInt("20");
			int d2 = Integer.parseInt(day1);
			int sumDay = days1 + d2;
			day1 = String.valueOf(sumDay);
			String pod1 = day1 + "/" + month1 + "/" + year1;
			System.out.println("ppp::" + pod1);
			
			Date date1 = formatter.parse(pod1);
			String expiryDate = formatter.format(date1);
			System.out.println("date after sum of days expirydate:" + expiryDate);
			return expiryDate;
		}
}
