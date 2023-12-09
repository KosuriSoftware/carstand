package com.zoro.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zoro.dto.AddCab;
import com.zoro.dto.AdvanceAmount;
import com.zoro.dto.AssignDriverCab;
import com.zoro.dto.CabBook;
import com.zoro.dto.CabCancellationPolicyBean;
import com.zoro.dto.CabHomeSearch;
import com.zoro.dto.CabRequester;
import com.zoro.dto.CabRoute;
import com.zoro.dto.DriverBean;
import com.zoro.dto.TravelerBooking;
import com.zoro.model.SearchCab;

public interface CabDAO {
	
	
	
	public Set<String> getCabModel();
	public Set<String> getNoOfPassenger(String model);
	public String addCab(AddCab addCab,InputStream rcDoc,InputStream insuranceDoc,InputStream cabPhoto);
	public String getCabRegNo(String cabRegNo);
	public String addCabRoutes(CabRoute cabRoute);
	public Set<String> CabRegNo(String email);
	public Map<String,String> getCabModel(String carRegNo);
	public String getCabIdFromCabRoute(String cabId);
	public List<CabRoute> searchCabRoutes(String email,String cabId);
	public String updateCabRoutes(CabRoute cabRoute);
	public String addDriver(DriverBean driver,InputStream doc,InputStream photo);
	public Set<String> getDriverId(String email);
	public List<DriverBean> searchDriver(String email,String driverId);
	public String updateDriver(DriverBean driver);
	public Map<String,String> getDriverName(String driverId);
	public String assignDriverToCab(String cabId,String driverId,String userId);
	public String getAssignedValue(String cabId,String email,String driverId);
	public String getDriverEmail(String driverEmail,String userId);
	public Set<AssignDriverCab> searchAssignedDriver(String cabId,String userId);
	public String activateDriver(String cabId,String driverId,String userId,String status);
	public String getCabRegNo(String cabRegNo,String email);
	public Map<String,String> getAssignedStatus(String driverId,String cabId);
	public Set<String> getBookingCabId(String email);
	public List<TravelerBooking> getBookedTraveller(String email,String cabId);
	public Map<String,Set<String>> getFromToVehicleType();
	public List<CabHomeSearch> searchHCab(SearchCab searchCab);
	public DriverBean getDriverNames(String driverId,String cabId);
	public String getcabId(String cabRegNo);
	public AddCab getCabDetails(String cabRegNo);
	public String insertCabBooking(CabBook cabBook,String cabId);
	public List<CabBook> getCabBookedDetails(String email);
	public CabBook cabBookingDetails(String bookingId);
	public String getCabRouteStatus(String cabId);
	public List<CabRoute> getCabRouteDetails(String query);
	public List<CabRoute> searchCabRoute(String cabId,String from,String to,String email);
	public String changeRouteStatus(String routeSeqId,String status);
	public String changeCabAvailability(String cabSeqId,String avl);
	public List<CabBook> getBookingDetails(String query);
	public List<CabBook> searchCabBooking(String cabId,String status,String email);
	public String changeBookingStatus(String cabId,String bookingId,String email,String status,String userEmail,String routId);
	public String confirmBookingStatus(String cabId, String bookingId, String email, String status,String routId);
	public String cancelCabBooking(String bookingId,String reason,String userType);
	public CabRoute cabRouteByRouteId(String routeId);
	public String updateBookingStatus(String bookingId,String status);
	public Set<String> getCabBookingStatus(String routeId);
	public List<AddCab> getCabFullDetails(String query);
	public List<AddCab> getCabFullDetails1(String query);//add by danish
	
	public List<AddCab> searchCDSBCab(String email,String cabId);
	public String getBusinessName(String email);
	public Set<AssignDriverCab> getDriverAssignedDetails(String query);
	public Set<AssignDriverCab> searchDriverAssigned(String cabId,String driverId,String userId);
	public String getCabStatus(String cabId);
	public List<AddCab> searAdCab(String email,String fromDate,String toDate);
	public List<CabBook> searchTrCabBooking(String date1, String date2, String email);
	public String addCabCancellationPrice(CabCancellationPolicyBean cancellationPolicy);
	public String addConfirmationDate(String bookingId,String time);
	public List<CabCancellationPolicyBean> getCabCancellaionData(String cabRegNo,String cabEmail);
	public Set<String> getCabId(String email);
	public String updatePrice(String routeId,String userId, String price, String pricePerKm);
	public List<CabRoute> searchCabRoute(String userId,String cabId, String date);
	public String insertRoutePrice(String routeId,String price,String pricepeerkm);
	public String insertUpdateCab(AddCab addCab,InputStream rcDoc,InputStream insuranceDoc,InputStream cabPhoto,InputStream rcDoc1,InputStream insuranceDoc1,InputStream cabPhoto1);
	public String getCabBookingReturnDate(String routeId,String travelDate);
	public CabBook getCabBookingDts(String cabId,String travelDate);
	public List<CabCancellationPolicyBean> viewCabCancellationPolicy(String email, String cabId);
	//Mahesh 
	public String addTravellerAdvanceAmount(AdvanceAmount adamt);
	public List<CabBook> getCabOwnerBookedCabs(CabBook cabbook,int offset,int noOfRecords);
	public int getNoOfRecords();
	public Set<String> getCOBookedCabIds(String email);
	public HashMap<String,Set<String>> getBodyTypeRegCabBrands();
	public String sendCabRequest(CabRequester cabRequester);
	public List<CabRequester> searchTravellerCabRequests(CabRequester cabRequester,int limit,int offset);
	public List<String> getCarBrands();
	public List<String> getCarColors();
	public int isCab_PlanActive(String cab_id); //Code Added By Virendra
	
}
