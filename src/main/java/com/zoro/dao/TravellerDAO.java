package com.zoro.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zoro.dto.RegistrationBean;
import com.zoro.dto.TravelerBooking;
import com.zoro.dto.Travellers;

public interface TravellerDAO {
	
	public String insertTravellerPost(Travellers traveller);
	public RegistrationBean viewProfile(String email);
	public String updateProfile(RegistrationBean reg);
	public Map<String,Set<String>> getTravellerCityAndVehicleType();
	public List<Travellers> searchHTraveller(String fromCity,String toCity,String date,String vehicleType,String location,String noOfPas);
	public String getCabId(String email,String cabId,String trId);
	public String insertTravelerBooking(List<String> list);
	public List<String> getTravellerBookingDetails(String cabId,String travellerId);
	public Travellers getTravelersDetails(String trId);
	public Map<String,Set<String>> getTravellerCityAndVehicleType(String email);
	public List<Travellers> searchTravelerPost(String from,String to,String date,String email);
	public String updateTravelerStatus(String trId,String email,String status);
	public Set<String> getTravelerId(String email);
	public List<TravelerBooking> getTravelerBooking(String email,String trId);
	public String travelerCancellationByCabOwner(String bookingId,String feedBack,String status,String postingId,String userId);
	public String updateTravelerBookingStatus(String status,String bookingId,String postingId);


}
