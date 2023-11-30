package com.zoro.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zoro.dto.AddCab;
import com.zoro.dto.AssignDriverCab;
import com.zoro.dto.CabBook;
import com.zoro.dto.CabRoute;
import com.zoro.dto.RegistrationBean;
import com.zoro.dto.TravelerBooking;
import com.zoro.dto.Travellers;
import com.zoro.dto.VehicleTariff;
import com.zoro.dto.VehicleType;

public interface AdminDAO {
	
	public List<RegistrationBean> getRegisteredDetails(String userType,String userId);
	public Set<String> getUserId(String userType);
	public String changeStatus(String status,String email);
	public String addVehicleType(String brand,String model,String email,String noOfPas,InputStream image,String bodyType);
	public List<VehicleType> searchVehicleType(String email);
	public Set<String> getVehicleType();
	public List<VehicleType> getAdminVehicleType();
	public Map<String,Set<String>> getAdCabModel(String brand);
	public Set<String> getNoOfPassenger(String model);
	public List<AddCab> getAdminCab(String email);
	public AddCab viewAdCab(String email,String cabId);
	public List<CabRoute> viewAdCabRout(String email,String cabId);
	public List<AddCab> getCabDetails(String query);
	public List<AddCab> getAdminCab(String email,String cabId);
	public List<CabBook> getCabBookingDetails(String query);
	public List<CabBook> serachCabBooking(String cabId,String email,String date1,String date2);
	public String getCabBookingCount(String cabId,String ownerId);
	public String getCandidatename(String email);
	public List<AssignDriverCab> viewCabDriver(String email,String driverId);
	public List<Travellers> searchAdTravelerPost(String email,String trId);
	public Set<String> getTravelerPostingId(String email);
	public String getTravelerBookingCount(String email,String trId);
	public List<TravelerBooking> searchTravellerBooking(String email,String trId,String date1,String date2);
	public List<TravelerBooking> getTravelerBookingDetails(String query);
	public String InsertCabFeedBack(String cabId,String status,String email,String feedBack);
	public String getInactiveCabCount();
	public String getCabRequest(String email);
	public String insertEmpRole(String empId,String empRole);
	public String getEmpRole(String email);
	public String updateRole(String empId,String role);
	public String updateVehicleType(VehicleType vehicle, InputStream image);
	
//	Mahesh Methods
	
	public String addTariff(VehicleTariff vehicleTariff);
	public VehicleTariff checkTariff(VehicleTariff vehicleTariff);
	public ArrayList<VehicleTariff> getAllTariff(VehicleTariff vt);
	public ArrayList<VehicleTariff> getAllTariff(VehicleTariff vt,int offset,int noOfRecords);
	public VehicleTariff getTariffDetails(VehicleTariff vehicleTariff);
	public boolean updateTariffDetails(VehicleTariff vehicleTariff);
	public ArrayList<VehicleTariff> searchVehicleTariff(VehicleTariff vt,int limit,int offset);
	public int getNoOfRecords();

}
