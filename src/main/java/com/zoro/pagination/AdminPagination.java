package com.zoro.pagination;

import java.util.List;

import com.zoro.dao.impl.AdminDAOImpl;
import com.zoro.dto.AddCab;
import com.zoro.dto.AssignDriverCab;
import com.zoro.dto.CabBook;
import com.zoro.dto.CabRoute;
import com.zoro.dto.RegistrationBean;
import com.zoro.dto.TravelerBooking;
import com.zoro.dto.Travellers;

public class AdminPagination {
	
	public List<RegistrationBean> getRegDetails(String userType,String userId){
		
		List<RegistrationBean> reg=new AdminDAOImpl().getRegisteredDetails(userType,userId);
		
		return reg;
	}
	
	public List<AddCab> searchAdminCab(String email){
		
		List<AddCab> cabList=new AdminDAOImpl().getAdminCab(email);
		
		return cabList;
		
	}
	
	public List<CabRoute> viewAdminCabRoute(String email,String cabId){
		
		List<CabRoute> routeList=new AdminDAOImpl().viewAdCabRout(email, cabId);
				
		return routeList;
		
	}
	
	public List<AddCab> searchAdminCab(String email,String cabId){
		
		List<AddCab> cabList=new AdminDAOImpl().getAdminCab(email, cabId);
		
		return cabList;
	}
	
	public List<CabBook> searchCabBooking(String email,String cabId,String date1,String date2){
		
		List<CabBook> cabBookingList=new AdminDAOImpl().serachCabBooking(cabId, email, date1, date2);
		
		return cabBookingList;
		
	}

	public List<AssignDriverCab> viewAssignDriverCab(String email,String driverId){
		
		List<AssignDriverCab> assignList=new AdminDAOImpl().viewCabDriver(email, driverId);
		
		return assignList;
	}
	
	public List<Travellers> searchAdTravelerPost(String email,String trId){
		
		List<Travellers> postList=new AdminDAOImpl().searchAdTravelerPost(email, trId);
		
		return postList;
	}
	
	public List<TravelerBooking> viewTravelerBooking(String email,String trId,String date1,String date2){
		
		List<TravelerBooking> bookList=new AdminDAOImpl().searchTravellerBooking(email, trId, date1, date2);
		
		return bookList;
		
	}
}
