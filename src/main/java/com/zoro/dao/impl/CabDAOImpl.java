package com.zoro.dao.impl;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.zoro.dao.CabDAO;
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
import com.zoro.sms.utilities.SmsCallGet;
import com.zoro.utilities.BookingId;
import com.zoro.utilities.CabId;
import com.zoro.utilities.DBConnection;
import com.zoro.utilities.IdGen;
import com.zoro.utilities.SQLDate;

@Component
public class CabDAOImpl implements CabDAO {

	private Connection con = null;
	private int noOfRecords = 0;

	public Set<String> getCabModel() {

		Set<String> cabModel = new TreeSet<String>();

		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_MODEL FROM cab_route where CAB_MODEL is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cabModel.add(rs.getString("CAB_MODEL"));

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabModel;
	}

	public Set<String> getNoOfPassenger(String model) {

		Set<String> noOfPassenger = new TreeSet<String>();

		PreparedStatement pst = null;

		try {
			String query = "SELECT NO_OF_PASSENGER FROM cab_route where CAB_MODEL='" + model
					+ "' and NO_OF_PASSENGER is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				noOfPassenger.add(rs.getString("NO_OF_PASSENGER"));

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return noOfPassenger;
	}

	public String addCab(AddCab addCab, InputStream rcDoc, InputStream insuranceDoc, InputStream cabPhoto) {
		String msg = null;
		PreparedStatement pst = null;

		try {
			String id = new IdGen().getId("CAB_ID");
			String cabId = new CabId().CabId(id);

			String query = "insert into add_cab values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, CURDATE(),?,?,?)";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);
			pst.setInt(1, addCab.getCAB_SEQ_ID());
			pst.setString(2, addCab.getCAB_REG_NO());
			pst.setString(3, addCab.getCAB_BRAND());
			pst.setString(4, addCab.getCAB_MODEL());
			pst.setString(5, addCab.getMODEL_YEAR());
			pst.setString(6, addCab.getCURRENT_MILEAGE());
			pst.setString(7, addCab.getFUEL_TYPE());
			pst.setString(8, addCab.getBODY_TYPE());
			pst.setString(9, addCab.getTRANSMISSION());
			pst.setString(10, addCab.getKM_DRIVEN());
			pst.setString(11, addCab.getNO_OF_PASSENGER());
			pst.setString(12, addCab.getCOLOR());
			pst.setString(13, addCab.getINSURENCE_COMP_NAME());
			pst.setString(14, addCab.getCERTIFIED_COMP_NAME());
			pst.setString(15, addCab.getREGISTERED_YEAR());
			pst.setString(16, addCab.getREGISTERED_CITY());
			pst.setString(17, addCab.getREGISTERED_STATE());

			// pst.setBlob(18, rcDoc);
			// pst.setBlob(19, insuranceDoc);
			// pst.setBlob(20, cabPhoto);

			pst.setBlob(18, rcDoc);
			pst.setBlob(19, insuranceDoc);
			pst.setBlob(20, cabPhoto);

			pst.setString(21, addCab.getCAB_OWNER_ID());
			pst.setString(22, cabId);
			pst.setString(23, "INACTIVE");
			pst.setString(24, addCab.getMOBILE_NO());
			pst.setString(25, addCab.getADDRESS());
			pst.setString(26, addCab.getCITY());
			pst.setString(27, addCab.getSTATE());
			pst.setString(28, addCab.getDISTRICT());
			pst.setString(29, addCab.getPINCODE());
			pst.setString(30, addCab.getCAB_PLATE_STATUS());

			// add new
			pst.setString(31, "0/00/0000");
			pst.setInt(32, 0);

//			pst.setString(31, "Full Cab");

//			pst.setString(33, "0");
//			pst.setInt(34, 0);

			int i = pst.executeUpdate();

			if (i > 0) {

				msg = "Cab" + " " + addCab.getCAB_REG_NO() + " " + "Added Successfully..";

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return msg;
	}

	public String getCabRegNo(String cabRegNo) {

		String regNo = null;
		String msg = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_REG_NO FROM add_cab where CAB_REG_NO='" + cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				regNo = rs.getString("CAB_REG_NO");

			}
			if (regNo != null) {

				msg = "This Cab Number is all ready added. Please try with other car number";

			}

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return msg;

	}

	public Set<String> CabRegNo(String email) {
		Set<String> regNo = new TreeSet<String>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_REG_NO FROM add_cab where CAB_OWNER_ID='" + email
					+ "' and CAB_REG_NO is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);
			System.out.println("Query --" + query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				regNo.add(rs.getString("CAB_REG_NO"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return regNo;
	}

	public Map<String, String> getCabModel(String cabRegNo) {
		Map<String, String> regNo = new TreeMap<String, String>();
		String msg = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_MODEL,NO_OF_PASSENGER,STATUS,CAB_EXPIRY_DATE FROM add_cab where CAB_REG_NO='"
					+ cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				regNo.put("cabModel", rs.getString("CAB_MODEL"));
				regNo.put("noOfPas", rs.getString("NO_OF_PASSENGER"));
				regNo.put("cabStatus", rs.getString("STATUS"));
				regNo.put("planExpiryDate", rs.getString("CAB_EXPIRY_DATE"));
				System.out.println(rs.getString("CAB_EXPIRY_DATE"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return regNo;
	}

	public String getCabIdFromCabRoute(String cabId) {

		String msg = null;
		String cabRegId = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_ID FROM cab_route where CAB_ID='" + cabId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cabRegId = rs.getString("CAB_ID");

			}
			if (cabRegId != null) {

				msg = "This Cab Number Is Allready Added In zorocabs Please Update Your Route";

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return msg;
	}

	public List<CabRoute> searchCabRoutes(String email, String cabId) {

		List<CabRoute> cabRoute = new ArrayList<CabRoute>();
		String msg = null;
		PreparedStatement pst = null;

		String query = null;

		if (cabId != null) {

			query = "SELECT * FROM cab_route where CAB_OWNER_ID='" + email + "' and CAB_ID='" + cabId
					+ "' and STATUS='ACTIVE'";

		} else {

			query = "SELECT * FROM cab_route where CAB_OWNER_ID='" + email + "' and STATUS='ACTIVE'";

		}

		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CabRoute routeBean = new CabRoute();

				routeBean.setCAB_ROUTE_ID(rs.getInt("CAB_ROUTE_ID"));
				routeBean.setCAB_MODEL(rs.getString("CAB_MODEL"));
				routeBean.setSOURCE_ADDRESS(rs.getString("SOURCE_ADDRESS"));
				routeBean.setDESTINATION(rs.getString("DESTINATION"));
				routeBean.setPICK_UP_DATE(rs.getString("PICK_UP_DATE"));
				routeBean.setFROM_LOCATION(rs.getString("FROM_LOCATION"));
				routeBean.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
				routeBean.setPRICE(rs.getString("PRICE"));
				routeBean.setPRICE_PER_KM(rs.getString("PRICE_PER_KM"));
				routeBean.setCAB_ID(rs.getString("CAB_ID"));
				routeBean.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				routeBean.setSTATUS(rs.getString("STATUS"));
				routeBean.setAVAILABILITY(rs.getString("AVAILABILITY"));
				routeBean.setROUTE_GEN_ID(rs.getString("ROUTE_GEN_ID"));
				routeBean.setROUTE_ADDED_TIME(rs.getString("ROUTE_ADDED_TIME"));
				routeBean.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));

				cabRoute.add(routeBean);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabRoute;
	}

	public Set<String> getDriverId(String email) {

		Set<String> driverId = new TreeSet<String>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT DRIVER_ID FROM add_driver where M_USER_ID='" + email + "' and DRIVER_ID is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				driverId.add(rs.getString("DRIVER_ID"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driverId;
	}

	public List<DriverBean> searchDriver(String email, String driverId) {
		List<DriverBean> driverList = new ArrayList<DriverBean>();
		String msg = null;
		PreparedStatement pst = null;

		String query = null;
		Blob blob1, blob2;
		byte[] licDoc = null, photo = null;
		String licenseDoc = null, image = null;
		if (driverId != null) {

			query = "SELECT * FROM add_driver where M_USER_ID='" + email + "' and DRIVER_ID='" + driverId + "'";

		} else {

			query = "SELECT * FROM add_driver where M_USER_ID='" + email + "'";

		}

		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				DriverBean driver = new DriverBean();

				driver.setFIRST_NAME(rs.getString("FIRST_NAME"));
				driver.setLAST_NAME(rs.getString("LAST_NAME"));
				driver.setEMAIL(rs.getString("EMAIL"));
				driver.setMOBILE_NO(rs.getString("MOBILE_NO"));
				driver.setDRIVER_ID(rs.getString("DRIVER_ID"));
				driver.setDOB(rs.getString("DOB"));
				driver.setADDRESS(rs.getString("ADDRESS"));
				driver.setSTREET(rs.getString("STREET"));
				driver.setCITY(rs.getString("CITY"));
				driver.setSTATE(rs.getString("STATE"));
				driver.setDISTRICT(rs.getString("DISTRICT"));
				driver.setPINCODE(rs.getString("PINCODE"));
				driver.setREGISTERED_STATE(rs.getString("REGISTERED_STATE"));
				driver.setLICENSE_NO(rs.getString("LICENSE_NO"));
				driver.setLICENSE_TYPE(rs.getString("LICENSE_TYPE"));
				driver.setEXPIRY_DATE(rs.getString("EXPIRY_DATE"));
				driver.setPERMIT_TYPE(rs.getString("PERMIT_TYPE"));
				driver.setWITHIN_RANGE(rs.getString("WITHIN_RANGE"));
				driver.setADHAR_NO(rs.getString("ADHAR_NO"));
				driver.setPAN_NO(rs.getString("PAN_NO"));
				driver.setJOB_TYPE(rs.getString("JOB_TYPE"));
				driver.setDRIVING_EXP(rs.getString("DRIVING_EXP"));
				driver.setREGISTERED_DATE(rs.getString("REGISTERED_DATE"));
				driver.setSTATUS(rs.getString("STATUS"));
				driver.setOWNER_ID(rs.getString("M_USER_ID"));

				blob1 = rs.getBlob("LICENSE_DOC");
				blob2 = rs.getBlob("PHOTO");
				if (blob1 != null) {
					licDoc = blob1.getBytes(1, (int) blob1.length());
					licenseDoc = Base64.getEncoder().encodeToString(licDoc);
				}
				if (blob2 != null) {
					photo = blob2.getBytes(1, (int) blob2.length());
					image = Base64.getEncoder().encodeToString(photo);
				}

				driver.setLICENSE_DOC(licenseDoc);
				driver.setPHOTO(image);

				driverList.add(driver);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driverList;
	}

	public Map<String, String> getDriverName(String driverId) {
		Map<String, String> driver = new TreeMap<String, String>();
		String msg = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT FIRST_NAME,LAST_NAME,EMAIL FROM add_driver where DRIVER_ID='" + driverId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				driver.put("driverName", rs.getString("FIRST_NAME") + "  " + rs.getString("LAST_NAME"));
				driver.put("driverEmail", rs.getString("EMAIL"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driver;
	}

	public String assignDriverToCab(String cabId, String driverId, String userId) {
		String msg = null;
		PreparedStatement pst = null;

		try {
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
			DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
			String sysDate = format.format(date);
			String sysTime = formatTime.format(date);

			String query = "insert into assign_driver_cab values(?,?,?,?,?,?,?)";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			pst.setInt(1, 0);
			pst.setString(2, driverId);
			pst.setString(3, cabId);
			pst.setString(4, userId);
			pst.setString(5, sysDate);
			pst.setString(6, sysTime);
			pst.setString(7, "ACTIVE");

			int i = pst.executeUpdate();

			if (i > 0) {

				msg = "Assigned Driver To Cab Successfully..";

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return msg;
	}

	public String getAssignedValue(String cabId, String email, String driverId1) {

		PreparedStatement pst = null;
		String driverId = null;

		try {
			String query = "SELECT DRIVER_ID FROM assign_driver_cab where CAB_ID='" + cabId + "' and CAB_OWNER_ID='"
					+ email + "' and DRIVER_ID='" + driverId1 + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				driverId = rs.getString("DRIVER_ID");

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driverId;
	}

	public String getDriverEmail(String driverEmail, String userId) {

		PreparedStatement pst = null;
		String driverId = null;

		try {
			String query = "SELECT EMAIL FROM add_driver where EMAIL='" + driverEmail + "' and M_USER_ID='" + userId
					+ "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				driverId = rs.getString("DRIVER_ID");

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driverId;
	}

	public Set<AssignDriverCab> searchAssignedDriver(String cabId, String userId) {

		String query = null;

		if (userId != null && (cabId != null && !cabId.equals("null")) && !cabId.equals("")) {

			query = "SELECT * FROM assign_driver_cab where CAB_ID='" + cabId + "' and CAB_OWNER_ID='" + userId
					+ "' group by ASSIGN_ID";

		} else {

			query = "SELECT * FROM assign_driver_cab where CAB_OWNER_ID='" + userId + "' group by ASSIGN_ID";

		}
		Set<AssignDriverCab> driverSet = getDriverAssignedDetails(query);

		return driverSet;
	}

	public String activateDriver(String cabId, String assignId, String userId, String status) {

		PreparedStatement pst = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {

			String query = "update assign_driver_cab set STATUS='" + status + "' where CAB_ID='" + cabId
					+ "' and ASSIGN_ID='" + assignId + "' and CAB_OWNER_ID='" + userId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			int i = pst.executeUpdate();
			if (i > 0) {
				if (status.equals("INACTIVE")) {
					message = "Driver Inactivated SuccessFully......";
				} else {
					message = "Driver Activated SuccessFully..";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public String getCabRegNo(String cabRegNo, String email) {

		PreparedStatement pst = null;
		String cabId = null;
		try {
			String query = "SELECT CAB_REG_NO FROM add_cab where CAB_OWNER_ID='" + email + "' and CAB_REG_NO='"
					+ cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cabId = rs.getString("CAB_REG_NO");

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabId;
	}

	public Map<String, String> getAssignedStatus(String driverId, String cabId) {

		PreparedStatement pst = null;
		Map<String, String> map = new TreeMap<String, String>();
		try {
			String query = "SELECT STATUS,CAB_ID FROM assign_driver_cab where DRIVER_ID='" + driverId
					+ "' and CAB_ID!='" + cabId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				map.put("status", rs.getString("STATUS"));
				map.put("cabId", rs.getString("CAB_ID"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	public Set<String> getBookingCabId(String email) {

		Set<String> cabSet = new TreeSet<String>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_ID FROM traveler_booking where CAB_OWNER_ID='" + email
					+ "' and CAB_ID is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cabSet.add(rs.getString("CAB_ID"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabSet;
	}

	public List<TravelerBooking> getBookedTraveller(String email, String cabId) {

		List<TravelerBooking> cabSet = new ArrayList<TravelerBooking>();
		PreparedStatement pst = null;

		String query = null;
		if (cabId != null && !cabId.equals("null")) {
			query = "SELECT * FROM traveler_booking where CAB_OWNER_ID='" + email + "' and CAB_ID='" + cabId + "'";
		} else {
			query = "SELECT * FROM traveler_booking where CAB_OWNER_ID='" + email + "'";
		}
		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				TravelerBooking bean = new TravelerBooking();
				bean.setBOOKING_SEQ_ID(rs.getInt("BOOKING_SEQ_ID"));
				bean.setCAB_ID(rs.getString("CAB_ID"));
				bean.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				bean.setTRAVELER_ID(rs.getString("TRAVELER_ID"));
				bean.setTRAVELER_EMAIL(rs.getString("TRAVELER_EMAIL"));
				bean.setOFFER_PRICE(rs.getString("OFFER_PRICE"));
				bean.setBOOKING_ID(rs.getString("BOOKING_ID"));
				bean.setBOOKING_DATE(new SQLDate().getInDate(rs.getString("BOOKING_DATE")));
				bean.setSTATUS(rs.getString("STATUS"));
				cabSet.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabSet;
	}

	public Map<String, Set<String>> getFromToVehicleType() {

		Map<String, Set<String>> traveller = new TreeMap<String, Set<String>>();

		Set<String> fromset = new TreeSet<String>();
		Set<String> toset = new TreeSet<String>();
		Set<String> vehicleSet = new TreeSet<String>();

		String msg = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT SOURCE_ADDRESS,DESTINATION,CAB_MODEL FROM cab_route where SOURCE_ADDRESS is not null and DESTINATION is not null and CAB_MODEL is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				fromset.add(rs.getString("SOURCE_ADDRESS"));
				toset.add(rs.getString("DESTINATION"));
				vehicleSet.add(rs.getString("CAB_MODEL"));

			}

			traveller.put("fromCity", fromset);
			traveller.put("toCity", toset);
			traveller.put("vehicleType", vehicleSet);
			System.out.println("Tra Sout : " + traveller);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return traveller;
	}

	public List<CabHomeSearch> searchHCab(SearchCab searchCab) {
		// TODO Auto-generated method stub

		String from = searchCab.getFromCity();
		String to = searchCab.getToCity();
		String date = searchCab.getDate();
		String vehicleType = searchCab.getVehicleType();
		String location = searchCab.getLocation();
		String plateType = searchCab.getPlateType();
		String driverType = searchCab.getDriverType();
		String seatingCap = searchCab.getSeatingCap();
		String serviceType = searchCab.getServiceType();
		String brandType = searchCab.getBrandType();
		String color = searchCab.getColor();

		System.out.println("Test line");
		List<CabHomeSearch> cabSearch = new ArrayList<CabHomeSearch>();
		String msg = null;
		PreparedStatement pst = null;
		String query = null;
		String query2 = null;
		System.out.println(vehicleType);
		if (vehicleType == null) {
			vehicleType = "";
		}

		if (location == null || location.equals("null")) {
			location = "";
		}
		if (plateType == null || plateType.equals("null")) {
			plateType = "";
		}
		if (seatingCap == null || seatingCap.equals("null")) {
			seatingCap = "";
		}
		if (driverType == null || driverType.equals("null")) {
			driverType = "";
		}

		Date date1 = new Date();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String date2 = format.format(date1);

		String query1 = "select rt.*,ad.CAB_BRAND,ad.MODEL_YEAR,ad.REGISTERED_YEAR,ad.CERTIFIED_COMP_NAME,ad.CAB_REG_NO,ad.CAB_GEN_ID,ad.COLOR,ad.CAB_PLATE_STATUS,ad.CAB_PHOTO,ad.BODY_TYPE, ass.DRIVER_ID,ass.CAB_ID,ass.STATUS as asstatus,dr.DRIVER_ID,dr.FIRST_NAME,dr.LAST_NAME,dr.LICENSE_NO,dr.REGISTERED_STATE,dr.DRIVING_EXP,dr.STATUS as drstatus,dr.JOB_TYPE,up.BUSINESS_NAME from cab_route rt left join add_cab ad on ad.CAB_REG_NO=rt.CAB_ID left join assign_driver_cab ass on ad.CAB_REG_NO=ass.CAB_ID left join add_driver dr on ass.DRIVER_ID=dr.DRIVER_ID left join update_reg_user up on ad.CAB_OWNER_ID=up.USER_EMAIL where rt.NO_OF_PASSENGER <=100 "; // Old
																																																																																																																																																																				// query
		// String query1="select
		// rt.*,ad.CAB_BRAND,ad.MODEL_YEAR,ad.REGISTERED_YEAR,ad.CERTIFIED_COMP_NAME,ad.CAB_REG_NO,ad.CAB_GEN_ID,ad.CAB_PLATE_STATUS,ad.CAB_PHOTO,ad.BODY_TYPE,
		// ass.DRIVER_ID,ass.CAB_ID,ass.STATUS as
		// asstatus,dr.DRIVER_ID,dr.FIRST_NAME,dr.LAST_NAME,dr.LICENSE_NO,dr.REGISTERED_STATE,dr.DRIVING_EXP,dr.STATUS
		// as drstatus,dr.JOB_TYPE,up.BUSINESS_NAME,cb.BOOKING_STATUS from cab_route rt
		// left join add_cab ad on ad.CAB_REG_NO=rt.CAB_ID left join assign_driver_cab
		// ass on ad.CAB_REG_NO=ass.CAB_ID left join add_driver dr on
		// ass.DRIVER_ID=dr.DRIVER_ID left join update_reg_user up on
		// ad.CAB_OWNER_ID=up.USER_EMAIL left join cab_booking cb on cb.CAB_ID=rt.CAB_ID
		// where cb.Booking_status <> 'CONFIRMED' " ;
		// String query1 = "select
		// rt.*,ad.CAB_BRAND,ad.MODEL_YEAR,ad.REGISTERED_YEAR,ad.CERTIFIED_COMP_NAME,ad.CAB_REG_NO,ad.CAB_GEN_ID,ad.COLOR,ad.CAB_PLATE_STATUS,ad.CAB_PHOTO,ad.BODY_TYPE,
		// ass.DRIVER_ID,ass.CAB_ID,ass.STATUS as asstatus,
		// dr.DRIVER_ID,dr.FIRST_NAME,dr.LAST_NAME,dr.LICENSE_NO,dr.REGISTERED_STATE,dr.DRIVING_EXP,dr.STATUS
		// as drstatus, dr.JOB_TYPE,up.BUSINESS_NAME from cab_route rt left join add_cab
		// ad on ad.CAB_REG_NO=rt.CAB_ID left join assign_driver_cab ass on
		// ad.CAB_REG_NO=ass.CAB_ID left join add_driver dr on
		// ass.DRIVER_ID=dr.DRIVER_ID left join update_reg_user up on
		// ad.CAB_OWNER_ID=up.USER_EMAIL left join cab_booking cb on ad.cab_reg_no =
		// cb.cab_id where cb.traveling_date <> '"+date+"' and cb.booking_status <>
		// 'confirmed'";
		// String query1 = "select
		// rt.*,ad.CAB_BRAND,ad.MODEL_YEAR,ad.REGISTERED_YEAR,ad.CERTIFIED_COMP_NAME,ad.CAB_REG_NO,ad.CAB_GEN_ID,ad.COLOR,ad.CAB_PLATE_STATUS,ad.CAB_PHOTO,ad.BODY_TYPE,
		// ass.DRIVER_ID,ass.CAB_ID,ass.STATUS as
		// asstatus,dr.DRIVER_ID,dr.FIRST_NAME,dr.LAST_NAME,dr.LICENSE_NO,dr.REGISTERED_STATE,dr.DRIVING_EXP,dr.STATUS
		// as drstatus,dr.JOB_TYPE,up.BUSINESS_NAME from cab_route rt left join add_cab
		// ad on ad.CAB_REG_NO=rt.CAB_ID left join assign_driver_cab ass on
		// ad.CAB_REG_NO=ass.CAB_ID left join add_driver dr on
		// ass.DRIVER_ID=dr.DRIVER_ID left join update_reg_user up on
		// ad.CAB_OWNER_ID=up.USER_EMAIL where ad.body_type = sedan ";
		System.out.println(date);

		/*
		 * if(from==null && to==null && date==null && vehicleType==null){
		 * 
		 * query=query1;
		 * 
		 * }else
		 */
		// if(!from.equals("") && !to.equals("") && !date.equals("") &&
		// !vehicleType.equals("") && location.equals(""))
		if (!from.equals("") && !to.equals("") && !date.equals("") && !vehicleType.equals("") && location.equals("")) {

			query2 = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and ad.BODY_TYPE='" + vehicleType + "' and rt.SERVICE_TYPE='"
					+ serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!from.equals("") && !to.equals("") && !date.equals("") && !vehicleType.equals("")
				&& location.equals("")) {

			// query2=query1+"and rt.SOURCE_ADDRESS='"+from+"' and (rt.DESTINATION='"+to+"'
			// || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='"+date+"') ||
			// (rt.PICK_UP_DATE='EveryDay')) and ad.BODY_TYPE='"+vehicleType+"' and
			// rt.SERVICE_TYPE='"+serviceType+"' and rt.STATUS='ACTIVE' and
			// rt.AVAILABILITY='YES'";

		} else if (!location.equals("") && plateType.equals("") && driverType.equals("") && seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.FROM_LOCATION='" + location
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!location.equals("") && !plateType.equals("") && driverType.equals("") && seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.FROM_LOCATION='" + location
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and ad.CAB_PLATE_STATUS='" + plateType
					+ "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!location.equals("") && plateType.equals("") && !driverType.equals("") && seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.FROM_LOCATION='" + location + "' and dr.JOB_TYPE='"
					+ driverType + "' and rt.SERVICE_TYPE='" + serviceType
					+ "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!location.equals("") && !plateType.equals("") && !driverType.equals("") && seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.FROM_LOCATION='" + location
					+ "' and ad.CAB_PLATE_STATUS='" + plateType + "' and dr.JOB_TYPE='" + driverType
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if ((!location.equals("") || !plateType.equals("") || !driverType.equals("")) && seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and (rt.FROM_LOCATION='" + location
					+ "' or ad.CAB_PLATE_STATUS='" + plateType + "' or dr.JOB_TYPE='" + driverType
					+ "') and rt.SERVICE_TYPE='" + serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!location.equals("") && !plateType.equals("") && driverType.equals("") && !seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.FROM_LOCATION='" + location
					+ "' and ad.CAB_PLATE_STATUS='" + plateType + "' and rt.NO_OF_PASSENGER='" + seatingCap
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!location.equals("") && plateType.equals("") && !driverType.equals("") && !seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.FROM_LOCATION='" + location
					+ "' and rt.NO_OF_PASSENGER='" + seatingCap + "' and dr.JOB_TYPE='" + driverType
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (location.equals("") && !plateType.equals("") && !driverType.equals("") && !seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and rt.NO_OF_PASSENGER='" + seatingCap
					+ "' and ad.CAB_PLATE_STATUS='" + plateType + "' and dr.JOB_TYPE='" + driverType
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else if (!seatingCap.equals("")) {

			query = query1 + "and rt.SOURCE_ADDRESS='" + from + "' and (rt.DESTINATION='" + to
					+ "' || rt.DESTINATION='Any') and ((rt.PICK_UP_DATE='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay')) and ((rt.FROM_LOCATION='" + location
					+ "' and rt.NO_OF_PASSENGER='" + seatingCap + "' and ad.CAB_PLATE_STATUS='" + plateType
					+ "' and rt.SERVICE_TYPE='" + serviceType + "' and dr.JOB_TYPE='" + driverType
					+ "') or (rt.FROM_LOCATION='" + location + "' and rt.NO_OF_PASSENGER='" + seatingCap
					+ "') or (ad.CAB_PLATE_STATUS='" + plateType + "' and rt.NO_OF_PASSENGER='" + seatingCap
					+ "') or (dr.JOB_TYPE='" + driverType + "' and rt.NO_OF_PASSENGER='" + seatingCap
					+ "') or  rt.NO_OF_PASSENGER='" + seatingCap
					+ "') and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'";

		} else {

			query = query1 + "and rt.STATUS='ACTIVE' and ((rt.PICK_UP_DATE>='" + date
					+ "') || (rt.PICK_UP_DATE='EveryDay'))  and rt.SERVICE_TYPE='" + serviceType
					+ "'and rt.STATUS='ACTIVE' and rt.AVAILABILITY='YES'  ";

		}
		/* Adding */
		if (brandType != null) {
			if (brandType != "") {
				query = query + " and ad.CAB_BRAND = '" + brandType + "' ";
			}

		}

		if (color != null) {
			if (color != "") {
				query = query + " and ad.COLOR = '" + color + "' ";
			}

		}
		/* Closing */

		if (serviceType != null && serviceType.equalsIgnoreCase("Self")) {

			query = query
					+ "and (ass.STATUS='ACTIVE' || ass.STATUS='INACTIVE' || ass.STATUS is null) group by rt.CAB_ROUTE_ID";

		} else {

			query = query + "and (ass.STATUS='ACTIVE' || ass.STATUS is null) group by rt.CAB_ROUTE_ID";
		}

		System.out.println("Query is :" + query2);

		Blob blob;
		byte[] image;
		/* Mahesh Adding Open */
		ResultSet rs1 = null;
		Statement st = null;
		String cabid = null;
		/* Mahesh Adding Close */
		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query2);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				CabHomeSearch routeBean = new CabHomeSearch();
				DriverBean driver = new DriverBean();
				AddCab cab = new AddCab();
				/* Mahesh adding Open */
				cabid = rs.getString("CAB_REG_NO");
//					System.out.println("Cab Id : "+cabid);
				st = con.createStatement();
				// System.out.println("SELECT CAB_ID,TRAVELING_DATE, BOOKING_STATUS from
				// cab_booking where CAB_ID = '"+cabid+"' and RETURNED_DATE < '"+date+"' order
				// by str_to_date(TRAVELING_DATE,'%d-%m-%Y') desc limit 1");
				// rs1=st.executeQuery("SELECT CAB_ID,TRAVELING_DATE, BOOKING_STATUS from
				// cab_booking where CAB_ID = '"+cabid+"' and RETURNED_DATE < '"+date+"' order
				// by str_to_date(TRAVELING_DATE,'%d-%m-%Y') desc limit 1");
				rs1 = st.executeQuery(query2);
				if (rs1.next()) {
//						System.out.println("rs1 1 : "+rs1.getString(1));
//						System.out.println("rs1 2 : "+rs1.getString(2));
					if (rs1.getString(3).equals("CONFIRMED")) {
//							System.out.println("Status Confirmed");
					} else {
//							System.out.println("In if");
						/* Mahesh adding Close */
						routeBean.setCAB_ROUTE_ID(rs.getInt("CAB_ROUTE_ID"));
						routeBean.setCAB_MODEL(rs.getString("CAB_MODEL"));
						routeBean.setSOURCE_ADDRESS(rs.getString("SOURCE_ADDRESS"));
						routeBean.setDESTINATION(rs.getString("DESTINATION"));
						/*
						 * if(serviceType!=null && !serviceType.equalsIgnoreCase("Full Cab")){
						 * routeBean.setPICK_UP_DATE(rs.getString("CAB_SHARING_DATE")); }else{
						 */
						routeBean.setPICK_UP_DATE(rs.getString("PICK_UP_DATE"));
//							}
						routeBean.setFROM_LOCATION(rs.getString("FROM_LOCATION"));
						routeBean.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
						routeBean.setPRICE(rs.getString("PRICE"));
						routeBean.setPRICE_PER_KM(rs.getString("PRICE_PER_KM"));
						routeBean.setCAB_ID(rs.getString("CAB_ID"));
						routeBean.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
						routeBean.setSTATUS(rs.getString("STATUS"));
						routeBean.setAVAILABILITY(rs.getString("AVAILABILITY"));
						routeBean.setDRIVER_ID(rs.getString("DRIVER_ID"));
						routeBean.setROUTE_GEN_ID(rs.getString("ROUTE_GEN_ID"));
						routeBean.setROUTE_ADDED_TIME(rs.getString("ROUTE_ADDED_TIME"));
						cab.setCAB_BRAND(rs.getString("CAB_BRAND"));
						cab.setMODEL_YEAR(rs.getString("MODEL_YEAR"));
						cab.setREGISTERED_YEAR(rs.getString("REGISTERED_YEAR"));
						cab.setCERTIFIED_COMP_NAME(rs.getString("CERTIFIED_COMP_NAME"));
						cab.setCAB_GEN_ID(rs.getString("CAB_GEN_ID"));
						cab.setCAB_PLATE_STATUS(rs.getString("CAB_PLATE_STATUS"));
						cab.setBODY_TYPE(rs.getString("BODY_TYPE"));
//							System.out.println("Color 4: "+rs.getString("COLOR"));
						cab.setCOLOR(rs.getString("COLOR"));
						routeBean.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));

						blob = rs.getBlob("CAB_PHOTO");
						String photo = null;
						if (blob != null) {
							image = blob.getBytes(1, (int) blob.length());
							photo = Base64.getEncoder().encodeToString(image);
						}
						cab.setCAB_PHOTO(photo);
						routeBean.setAddCab(cab);
						driver.setDRIVER_ID(rs.getString("DRIVER_ID"));
						driver.setFIRST_NAME(rs.getString("FIRST_NAME"));
						driver.setLAST_NAME(rs.getString("LAST_NAME"));
						driver.setLICENSE_NO(rs.getString("LICENSE_NO"));
						driver.setREGISTERED_STATE(rs.getString("REGISTERED_STATE"));
						driver.setDRIVING_EXP(rs.getString("DRIVING_EXP"));
						driver.setSTATUS(rs.getString("asstatus"));
						driver.setJOB_TYPE(rs.getString("JOB_TYPE"));
						routeBean.setDriver(driver);
						routeBean.setBUSINESS_NAME(rs.getString("BUSINESS_NAME"));
						cabSearch.add(routeBean);
						/* Mahesh adding Open */
					}
				} else {
//						System.out.println("In else");
					routeBean.setCAB_ROUTE_ID(rs.getInt("CAB_ROUTE_ID"));
					routeBean.setCAB_MODEL(rs.getString("CAB_MODEL"));
					routeBean.setSOURCE_ADDRESS(rs.getString("SOURCE_ADDRESS"));
					routeBean.setDESTINATION(rs.getString("DESTINATION"));
					/*
					 * if(serviceType!=null && !serviceType.equalsIgnoreCase("Full Cab")){
					 * routeBean.setPICK_UP_DATE(rs.getString("CAB_SHARING_DATE")); }else{
					 */
					routeBean.setPICK_UP_DATE(rs.getString("PICK_UP_DATE"));
//						}
					routeBean.setFROM_LOCATION(rs.getString("FROM_LOCATION"));
					routeBean.setNO_OF_PASSENGER(rs.getString("NO_OF_PASSENGER"));
					routeBean.setPRICE(rs.getString("PRICE"));
					routeBean.setPRICE_PER_KM(rs.getString("PRICE_PER_KM"));
					routeBean.setCAB_ID(rs.getString("CAB_ID"));
					routeBean.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
					routeBean.setSTATUS(rs.getString("STATUS"));
					routeBean.setAVAILABILITY(rs.getString("AVAILABILITY"));
					routeBean.setDRIVER_ID(rs.getString("DRIVER_ID"));
					routeBean.setROUTE_GEN_ID(rs.getString("ROUTE_GEN_ID"));
					routeBean.setROUTE_ADDED_TIME(rs.getString("ROUTE_ADDED_TIME"));
					cab.setCAB_BRAND(rs.getString("CAB_BRAND"));
					cab.setMODEL_YEAR(rs.getString("MODEL_YEAR"));
					cab.setREGISTERED_YEAR(rs.getString("REGISTERED_YEAR"));
					cab.setCERTIFIED_COMP_NAME(rs.getString("CERTIFIED_COMP_NAME"));
					cab.setCAB_GEN_ID(rs.getString("CAB_GEN_ID"));
					cab.setCAB_PLATE_STATUS(rs.getString("CAB_PLATE_STATUS"));
					cab.setBODY_TYPE(rs.getString("BODY_TYPE"));
//						System.out.println("Color 6: "+rs.getString("COLOR"));
					cab.setCOLOR(rs.getString("COLOR"));
					routeBean.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));

					blob = rs.getBlob("CAB_PHOTO");
					String photo = null;
					if (blob != null) {
						image = blob.getBytes(1, (int) blob.length());
						photo = Base64.getEncoder().encodeToString(image);
					}
					cab.setCAB_PHOTO(photo);
					routeBean.setAddCab(cab);
					driver.setDRIVER_ID(rs.getString("DRIVER_ID"));
					driver.setFIRST_NAME(rs.getString("FIRST_NAME"));
					driver.setLAST_NAME(rs.getString("LAST_NAME"));
					driver.setLICENSE_NO(rs.getString("LICENSE_NO"));
					driver.setREGISTERED_STATE(rs.getString("REGISTERED_STATE"));
					driver.setDRIVING_EXP(rs.getString("DRIVING_EXP"));
					driver.setSTATUS(rs.getString("asstatus"));
					driver.setJOB_TYPE(rs.getString("JOB_TYPE"));
					routeBean.setDriver(driver);
					routeBean.setBUSINESS_NAME(rs.getString("BUSINESS_NAME"));
					cabSearch.add(routeBean);
				}
				rs1.close();
				st.close();
				/* Mahesh adding Close */
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pst.close();
				// con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabSearch;
	}

	public String getcabId(String cabRegNo) {

		String cabId = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_GEN_ID FROM add_cab where CAB_REG_NO='" + cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cabId = rs.getString("CAB_GEN_ID");

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabId;
	}

	// method added by mahesh kosuri. To get mobile number and passing to cab
	// verification message.
	public String getCabContactNo(String cabRegNo) {

		String cab_contact_no = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT MOBILE_NO from add_cab where CAB_REG_NO='" + cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cab_contact_no = rs.getString("MOBILE_NO");

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cab_contact_no;
	}

	// method added by arti To get mobile number By CabId.
	public String getCabContactNoByCabId(int cabId) {

		String cab_contact_no = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT MOBILE_NO from add_cab where CAB_SEQ_ID='" + cabId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cab_contact_no = rs.getString("MOBILE_NO");

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cab_contact_no;
	}

	// method added by arti To get cab added date By CabId.
	public String getCabAddedDateByCabRegNo(String cabRegNo) {

		String cab_added_date = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_ADDED_DATE from add_cab where CAB_REG_NO='" + cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cab_added_date = rs.getString("CAB_ADDED_DATE");

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cab_added_date;
	}

	public AddCab getCabDetails(String cabRegNo) {

		PreparedStatement pst = null;

		AddCab addCab = new AddCab();
		Blob blob;
		byte[] image;
		try {
			String query = "SELECT * FROM add_cab where CAB_REG_NO='" + cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

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
				addCab.setCAB_PLATE_STATUS(rs.getString("CAB_PLATE_STATUS"));
				addCab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));

				blob = rs.getBlob("CAB_PHOTO");
				image = blob.getBytes(1, (int) blob.length());
				String photo = Base64.getEncoder().encodeToString(image);

				addCab.setCAB_PHOTO(photo);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return addCab;
	}

	public String insertCabBooking(CabBook cabBook, String cabOwner) {

		String bookingId = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
//		PreparedStatement pst1=null;

		try {
			String bookId = new IdGen().getId("CAB_BOOKING_ID");
			String cabBookId = new BookingId().cabBookingId(bookId);
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			String date1 = format.format(date);

			String query = "INSERT INTO `cab_booking` (`CAB_BOOKING_SEQ_NO`, `USER_EMAIL`, `CONTACT_NO`, `PICK_UP_ADDRESS`, `BOOKING_ID`, `BOOKING_DATE`, `BOOKING_STATUS`, `CAB_ID`, `CAB_OWNER_ID`, `DROPPING_ADDRESS`, `CAB_ROUTE_ID`, `RETURNED_DATE`, `CAB_SERVICE_TYPE`, `TRAVELING_DATE`, `PICK_UP_TIME`, `FROM_CITY`, `TO_CITY`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String query1 = "insert into cab_booking_confirmation_time values(?,?,?,?)";
//			String query1="update cab_route set RIDING_TYPE='BOOKED',STATUS='INACTIVE' where CAB_ID='"+cabBook.getCAB_ID()+"' and PICK_UP_DATE='"+date1+"' and STATUS='ACTIVE'";

			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(query);
			pst1 = con.prepareStatement(query1);
//			pst1=con.prepareStatement(query1);

			pst.setInt(1, 0);
			pst.setString(2, cabBook.getUSER_EMAIL());
			pst.setString(3, cabBook.getCONTACT_NO());
			pst.setString(4, cabBook.getPICK_UP_ADDRESS());
			pst.setString(5, cabBookId);
			pst.setString(6, date1);
			pst.setString(7, "BOOKED");
			pst.setString(8, cabBook.getCAB_ID());
			pst.setString(9, cabOwner);
			pst.setString(10, cabBook.getDROPPING_ADDRESS());
			pst.setString(11, cabBook.getCAB_ROUTE_ID());
			pst.setString(12, cabBook.getRETURNED_DATE());
			pst.setString(13, cabBook.getCAB_SERVICE_TYPE());
			pst.setString(14, cabBook.getTRAVELING_DATE());
			pst.setString(15, cabBook.getPICK_UP_TIME());
			pst.setString(16, cabBook.getFROM_CITY());
			pst.setString(17, cabBook.getTO_CITY());

			int i = pst.executeUpdate();
//			int j=pst1.executeUpdate();

			pst1.setInt(1, 0);
			pst1.setString(2, cabBookId);
			pst1.setString(3, cabBook.getBOOKING_ID());
			pst1.setString(4, cabBook.getBOOKING_DATE());

			int j = pst1.executeUpdate();
			if (i > 0 && j > 0) {
				con.commit();
				bookingId = cabBookId;

			} else {
				con.rollback();
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				pst1.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return bookingId;
	}

	public List<CabBook> getCabBookedDetails(String email) {

		List<CabBook> bookList = new ArrayList<CabBook>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT * FROM cab_booking where  USER_EMAIL='" + email + "' or CAB_OWNER_ID='" + email
					+ "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CabBook cab = new CabBook();

				cab.setBOOKING_ID(rs.getString("BOOKING_ID"));
				cab.setCONTACT_NO(rs.getString("CONTACT_NO"));
				cab.setPICK_UP_ADDRESS(rs.getString("PICK_UP_ADDRESS"));
				cab.setBOOKING_STATUS(rs.getString("BOOKING_STATUS"));
				cab.setBOOKING_DATE(rs.getString("BOOKING_DATE"));
				cab.setCAB_ID(rs.getString("CAB_ID"));
				cab.setRETURNED_DATE(rs.getString("RETURNED_DATE"));
				cab.setCAB_SERVICE_TYPE(rs.getString("CAB_SERVICE_TYPE"));
				cab.setTRAVELING_DATE(rs.getString("TRAVELING_DATE"));
				cab.setPICK_UP_TIME(rs.getString("PICK_UP_TIME"));
				cab.setFROM_CITY(rs.getString("FROM_CITY"));
				cab.setTO_CITY(rs.getString("TO_CITY"));
				bookList.add(cab);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bookList;
	}

	public CabBook cabBookingDetails(String bookingId) {

		CabBook cab = new CabBook();
		PreparedStatement pst = null;

		try {
			String query = "SELECT * FROM cab_booking where BOOKING_ID='" + bookingId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cab.setBOOKING_ID(rs.getString("BOOKING_ID"));
				cab.setCONTACT_NO(rs.getString("CONTACT_NO"));
				cab.setPICK_UP_ADDRESS(rs.getString("PICK_UP_ADDRESS"));
				cab.setBOOKING_STATUS(rs.getString("BOOKING_STATUS"));
				cab.setBOOKING_DATE(rs.getString("BOOKING_DATE"));
				cab.setCAB_ID(rs.getString("CAB_ID"));
				cab.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				cab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				cab.setCAB_ROUTE_ID(rs.getString("CAB_ROUTE_ID"));
				cab.setDROPPING_ADDRESS(rs.getString("DROPPING_ADDRESS"));
				cab.setTRAVELING_DATE(rs.getString("TRAVELING_DATE"));
				cab.setRETURNED_DATE(rs.getString("RETURNED_DATE"));
				cab.setFROM_CITY(rs.getString("FROM_CITY"));
				cab.setTO_CITY(rs.getString("TO_CITY"));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cab;
	}

	public String getCabRouteStatus(String cabId) {

		String status = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT STATUS FROM cab_route where CAB_ID='" + cabId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				status = rs.getString("STATUS");

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	public List<CabRoute> getCabRouteDetails(String query) {

		List<CabRoute> routeList = new ArrayList<CabRoute>();
		PreparedStatement pst = null;

		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				CabRoute route = new CabRoute();

				route.setCAB_ROUTE_ID(rs.getInt("CAB_ROUTE_ID"));
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
				route.setROUTE_GEN_ID(rs.getString("ROUTE_GEN_ID"));
				route.setROUTE_ADDED_TIME(rs.getString("ROUTE_ADDED_TIME"));
				route.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));

				routeList.add(route);

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return routeList;
	}

	public List<CabRoute> searchCabRoute(String cabId, String from, String to, String email) {

		String query = "";

		if (cabId.equals("All") && from.equals("") && to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "'";

		} else if ((!cabId.equals("") && !cabId.equals("All")) && !from.equals("") && !to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "' and CAB_ID='" + cabId
					+ "' and SOURCE_ADDRESS='" + from + "' and DESTINATION='" + to + "'";

		} else if ((cabId.equals("") || cabId.equals("All")) && !from.equals("") && !to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "' and SOURCE_ADDRESS='" + from
					+ "' and DESTINATION='" + to + "'";

		} else if (!cabId.equals("") && !from.equals("") && to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "' and CAB_ID='" + cabId
					+ "' and SOURCE_ADDRESS='" + from + "'";

		} else if (!cabId.equals("") && from.equals("") && !to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "' and CAB_ID='" + cabId
					+ "' and DESTINATION='" + to + "'";

		} else if ((!cabId.equals("") || !cabId.equals("All")) || !from.equals("") || !to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "' and ('" + cabId + "' in (CAB_ID)) || ('"
					+ from + "' in (SOURCE_ADDRESS)) || ('" + to + "' in (DESTINATION))";

		} else if ((cabId.equals("") || cabId.equals("All")) || !from.equals("") || !to.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + email + "' and ('" + from
					+ "' in (SOURCE_ADDRESS)) || ('" + to + "' in (DESTINATION))";

		} else {
			query = "select * from cab_route where CAB_OWNER_ID='" + email + "'";
		}

		List<CabRoute> routeList = getCabRouteDetails(query);

		return routeList;
	}

	public String changeRouteStatus(String routeSeqId, String status) {

		PreparedStatement pst = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {

			String query = "update cab_route set STATUS='" + status + "' where CAB_ROUTE_ID='" + routeSeqId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			int i = pst.executeUpdate();
			if (i > 0) {
				if (status.equals("INACTIVE")) {
					message = "Route Inactivated SuccessFully......";
				} else {
					message = "Route Activated SuccessFully..";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public String changeCabAvailability(String cabSeqId, String avl) {

		PreparedStatement pst = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {

			String query = "update cab_route set AVAILABILITY='" + avl + "' where CAB_ROUTE_ID='" + cabSeqId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			int i = pst.executeUpdate();
			if (i > 0) {
				if (avl.equals("NO")) {
					message = "Your Cab Is Not Available Now......";
				} else {
					message = "Your Cab Is Available Now..";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public List<CabBook> getBookingDetails(String query) {

		List<CabBook> bookList = new ArrayList<CabBook>();
		PreparedStatement pst = null;

		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				CabBook book = new CabBook();
				book.setCAB_BOOKING_SEQ_NO(rs.getInt("CAB_BOOKING_SEQ_NO"));
				book.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				book.setCONTACT_NO(rs.getString("CONTACT_NO"));
				book.setPICK_UP_ADDRESS(rs.getString("PICK_UP_ADDRESS"));
				book.setBOOKING_ID(rs.getString("BOOKING_ID"));
				book.setBOOKING_DATE(rs.getString("BOOKING_DATE"));
				book.setBOOKING_STATUS(rs.getString("BOOKING_STATUS"));
				book.setCAB_ID(rs.getString("CAB_ID"));
				book.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				book.setDROPPING_ADDRESS(rs.getString("DROPPING_ADDRESS"));
				book.setCAB_ROUTE_ID(rs.getString("CAB_ROUTE_ID"));
				book.setRETURNED_DATE(rs.getString("RETURNED_DATE"));
				book.setCAB_SERVICE_TYPE(rs.getString("CAB_SERVICE_TYPE"));
				book.setTRAVELING_DATE(rs.getString("TRAVELING_DATE"));
				book.setPICK_UP_TIME(rs.getString("PICK_UP_TIME"));
				book.setFROM_CITY(rs.getString("FROM_CITY"));
				book.setTO_CITY(rs.getString("TO_CITY"));

				bookList.add(book);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bookList;
	}

	public List<CabBook> searchCabBooking(String cabId, String status, String email) {

		String query = "";
		if (cabId.equals("All") && status.equals("")) {
			query = "select * from cab_booking where CAB_OWNER_ID='" + email + "'";
		} else if (cabId.equals("All") && !status.equals("")) {
			query = "select * from cab_booking where BOOKING_STATUS='" + status + "' and CAB_OWNER_ID='" + email + "'";
		} else if (!cabId.equals("") && !status.equals("")) {
			query = "select * from cab_booking where CAB_ID='" + cabId + "' and BOOKING_STATUS='" + status
					+ "' and CAB_OWNER_ID='" + email + "'";
		} else if (!cabId.equals("")) {
			query = "select * from cab_booking where CAB_ID='" + cabId + "' and CAB_OWNER_ID='" + email + "'";
		} else if (!status.equals("")) {
			query = "select * from cab_booking where BOOKING_STATUS='" + status + "' and CAB_OWNER_ID='" + email + "'";
		} else {
			query = "select * from cab_booking where CAB_OWNER_ID='" + email + "'";
		}
		List<CabBook> bookList = getBookingDetails(query);

		return bookList;
	}

	public String changeBookingStatus(String cabId, String bookingId, String email, String status, String userEmail,
			String routId) {

		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());

		try {
			String query = "update cab_route set STATUS='ACTIVE' where CAB_ID='" + cabId + "' and CAB_OWNER_ID='"
					+ email + "' and ROUTE_GEN_ID='" + routId + "'";
			String query1 = "update cab_booking set BOOKING_STATUS='" + status + "' where BOOKING_ID='" + bookingId
					+ "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);
			pst1 = con.prepareStatement(query1);

			int i = pst.executeUpdate();
			int j = pst1.executeUpdate();

			if (i > 0 && j > 0) {

				if (status.equals("CANCELED")) {
					message = "Booking Canceled Successfully...";
				} else {
					message = "Booking Rejected successfully..";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				pst1.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public String confirmBookingStatus(String cabId, String bookingId, String email, String status, String routId) {

		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {
			String query = "update cab_booking set BOOKING_STATUS='" + status + "' where BOOKING_ID='" + bookingId
					+ "'";
			String query1 = "update cab_route set STATUS='ACTIVE' where CAB_ID='" + cabId + "' and ROUTE_GEN_ID='"
					+ routId + "' and STATUS='ACTIVE'";

			con = DBConnection.getConnection();

			pst = con.prepareStatement(query);
			pst1 = con.prepareStatement(query1);

			int i = pst.executeUpdate();
			int j = pst1.executeUpdate();

			if (status != null && status.equals("CONFIRMED")) {
				if (i > 0 && j > 0) {

					message = "Booking Confirmed Successfully...";

				}
			} else if (status != null && status.equals("COMPLETED")) {
				if (i > 0) {
					message = "Booking Completed Successfully...";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				pst1.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public String cancelCabBooking(String bookingId, String reason, String userType) {

		String msg = null;
		PreparedStatement pst = null;

		try {

			String query = "insert into cab_booking_cancellation values(?,?,?,?,?)";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);
			pst.setInt(1, 0);
			pst.setString(2, bookingId);
			pst.setString(3, reason);
			pst.setString(4, userType);
			pst.setString(5, "CANCELED");

			int i = pst.executeUpdate();

			if (i > 0) {

				msg = "Booking Canceled Successfully..";

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return msg;
	}

	public CabRoute cabRouteByRouteId(String routeId) {

		String query = "select * from cab_route where ROUTE_GEN_ID='" + routeId + "'";

		List<CabRoute> routeList = getCabRouteDetails(query);
		CabRoute route = null;
		for (int i = 0; i < routeList.size(); i++) {
			route = routeList.get(i);
		}

		return route;
	}

	public String updateBookingStatus(String bookingId, String status) {

		PreparedStatement pst = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {
			String query = "update cab_booking set BOOKING_STATUS='" + status + "' where BOOKING_ID='" + bookingId
					+ "'";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			int i = pst.executeUpdate();

			if (i > 0) {

				message = "Booking Completed Successfully,Please Update Your Route Here!.";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public Set<String> getCabBookingStatus(String routeId) {

		Set<String> status = new TreeSet<String>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT BOOKING_STATUS FROM cab_booking where CAB_ROUTE_ID='" + routeId
					+ "' and BOOKING_STATUS is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				status.add(rs.getString("BOOKING_STATUS"));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	// add code by danish

	public List<AddCab> getCabFullDetails1(String query) {

		PreparedStatement pst = null;

		List<AddCab> cabList = new ArrayList<AddCab>();
		Blob blob, blob1, blob2;
		byte[] image, doc, ins;
		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				AddCab addCab = new AddCab();

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
				addCab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				addCab.setCAB_PLATE_STATUS(rs.getString("CAB_PLATE_STATUS"));
				addCab.setCAB_ADDED_DATE(rs.getString("CAB_ADDED_DATE"));

//					// add CODE by danish
//					addCab.setReqId(rs.getInt("membership_request_id"));
//					if(rs.getString("plan_expiry_date")!=null) {
//						System.out.println("date"+rs.getString("plan_expiry_date"));
//						addCab.setPlan_expiry_date(rs.getString("plan_expiry_date"));
//					}else {
//						addCab.setPlan_expiry_date("00/00/0000");
//					}
//					
//					
//					
//					addCab.setPlanDuration(rs.getString("plan_duration"));
//					addCab.setPlan_id(rs.getInt("membership_plan_id"));
//					// END add CODE by danish
//					

				blob = rs.getBlob("CAB_PHOTO");
				image = blob.getBytes(1, (int) blob.length());
				String photo = Base64.getEncoder().encodeToString(image);

				blob1 = rs.getBlob("RC_DOC");
				doc = blob1.getBytes(1, (int) blob1.length());
				String rcDoc = Base64.getEncoder().encodeToString(doc);

				blob2 = rs.getBlob("INSURANCE_DOC");
				ins = blob2.getBytes(1, (int) blob2.length());
				String incImage = Base64.getEncoder().encodeToString(ins);

				addCab.setCAB_PHOTO(photo);
				addCab.setRC_DOC(rcDoc);
				addCab.setINSURANCE_DOC(incImage);
//					addCab.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));

				cabList.add(addCab);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabList;
	}
	// end add code by danish

	public List<AddCab> getCabFullDetails(String query) {

		PreparedStatement pst = null;

		List<AddCab> cabList = new ArrayList<AddCab>();
		Blob blob, blob1, blob2;
		byte[] image, doc, ins;
		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				AddCab addCab = new AddCab();

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
				addCab.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				addCab.setCAB_PLATE_STATUS(rs.getString("CAB_PLATE_STATUS"));
				addCab.setCAB_ADDED_DATE(rs.getString("CAB_ADDED_DATE"));

				// add CODE by danish
				addCab.setReqId(rs.getInt("membership_request_id"));
				if (rs.getString("plan_expiry_date") != null) {
					System.out.println("date" + rs.getString("plan_expiry_date"));
					addCab.setPlan_expiry_date(rs.getString("plan_expiry_date"));
				} else {
					addCab.setPlan_expiry_date("00/00/0000");
				}

				addCab.setPlanDuration(rs.getString("plan_duration"));
				addCab.setPlan_id(rs.getInt("membership_plan_id"));
				// END add CODE by danish

				blob = rs.getBlob("CAB_PHOTO");
				image = blob.getBytes(1, (int) blob.length());
				String photo = Base64.getEncoder().encodeToString(image);

				blob1 = rs.getBlob("RC_DOC");
				doc = blob1.getBytes(1, (int) blob1.length());
				String rcDoc = Base64.getEncoder().encodeToString(doc);

				blob2 = rs.getBlob("INSURANCE_DOC");
				ins = blob2.getBytes(1, (int) blob2.length());
				String incImage = Base64.getEncoder().encodeToString(ins);

				addCab.setCAB_PHOTO(photo);
				addCab.setRC_DOC(rcDoc);
				addCab.setINSURANCE_DOC(incImage);
//					addCab.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));

				cabList.add(addCab);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabList;
	}

	public List<AddCab> searchCDSBCab(String email, String cabId) {

		String query = null;

		if (email != null && cabId != null && !cabId.equals("All") && !cabId.equals("") && !email.equals("")) {

			// query="select * from add_cab where CAB_OWNER_ID='"+email+"' and
			// CAB_REG_NO='"+cabId+"'";
			query = "select ac.CAB_SEQ_ID,ac.CAB_REG_NO,ac.CAB_BRAND,ac.CAB_MODEL,ac.MODEL_YEAR,ac.CURRENT_MILEAGE,ac.FUEL_TYPE,ac.BODY_TYPE,ac.TRANSMISSION,ac.KM_DRIVEN,ac.NO_OF_PASSENGER,ac.COLOR,ac.INSURENCE_COMP_NAME,ac.CERTIFIED_COMP_NAME,ac.REGISTERED_YEAR,ac.REGISTERED_CITY,ac.REGISTERED_STATE,ac.RC_DOC,ac.INSURANCE_DOC,ac.CAB_PHOTO,ac.CAB_OWNER_ID,ac.CAB_GEN_ID,ac.STATUS,ac.MOBILE_NO,ac.ADDRESS,ac.CITY,ac.STATE,ac.DISTRICT,ac.PINCODE,ac.CAB_ADDED_DATE,ac.CAB_PLATE_STATUS,cmd.plan_duration,cmd.plan_expiry_date,cmd.membership_request_id,cmd.membership_plan_id  from add_cab ac  LEFT JOIN cab_membership_request_details cmd on ac.CAB_SEQ_ID=cmd.add_cab_id where ac.CAB_OWNER_ID='"
					+ email + "' and ac.CAB_REG_NO='" + cabId + "'";

		} else if (email != null) {

			// query="select * from add_cab where CAB_OWNER_ID='"+email+"'";
			query = "select ac.CAB_SEQ_ID,ac.CAB_REG_NO,ac.CAB_BRAND,ac.CAB_MODEL,ac.MODEL_YEAR,ac.CURRENT_MILEAGE,ac.FUEL_TYPE,ac.BODY_TYPE,ac.TRANSMISSION,ac.KM_DRIVEN,ac.NO_OF_PASSENGER,ac.COLOR,ac.INSURENCE_COMP_NAME,ac.CERTIFIED_COMP_NAME,ac.REGISTERED_YEAR,ac.REGISTERED_CITY,ac.REGISTERED_STATE,ac.RC_DOC,ac.INSURANCE_DOC,ac.CAB_PHOTO,ac.CAB_OWNER_ID,ac.CAB_GEN_ID,ac.STATUS,ac.MOBILE_NO,ac.ADDRESS,ac.CITY,ac.STATE,ac.DISTRICT,ac.PINCODE,ac.CAB_ADDED_DATE,ac.CAB_PLATE_STATUS,cmd.plan_duration,cmd.plan_expiry_date,cmd.membership_request_id,cmd.membership_plan_id  from add_cab ac LEFT JOIN cab_membership_request_details cmd on ac.CAB_SEQ_ID=cmd.add_cab_id where ac.CAB_OWNER_ID='"
					+ email + "'";
		}
		List<AddCab> cabList = getCabFullDetails(query);

		return cabList;
	}

	public String getBusinessName(String email) {

		String businessName = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT BUSINESS_NAME FROM update_reg_user where USER_EMAIL='" + email + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				businessName = rs.getString("BUSINESS_NAME");
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return businessName;
	}

	public Set<AssignDriverCab> getDriverAssignedDetails(String query) {

		Set<AssignDriverCab> driverSet = new HashSet<AssignDriverCab>();
		String msg = null;
		PreparedStatement pst = null;

		try {
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				AssignDriverCab assignDriver = new AssignDriverCab();

				assignDriver.setASSIGN_ID(rs.getInt("ASSIGN_ID"));
				assignDriver.setCAB_ID(rs.getString("CAB_ID"));
				assignDriver.setDRIVER_ID(rs.getString("DRIVER_ID"));
				assignDriver.setASSIGN_DATE(rs.getString("ASSIGN_DATE"));
				assignDriver.setASSIGN_TIME(rs.getString("ASSIGN_TIME"));
				assignDriver.setSTATUS(rs.getString("STATUS"));

				driverSet.add(assignDriver);

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driverSet;
	}

	public Set<AssignDriverCab> searchDriverAssigned(String cabId, String driverId, String userId) {

		String query = null;

		if (!cabId.equals("") && !cabId.equals("All") && driverId.equals("")) {

			query = "SELECT * FROM assign_driver_cab where CAB_ID='" + cabId + "' and CAB_OWNER_ID='" + userId
					+ "' group by ASSIGN_ID";

		} else if (cabId.equals("") && !driverId.equals("")) {

			query = "SELECT * FROM assign_driver_cab where DRIVER_ID='" + driverId + "' and CAB_OWNER_ID='" + userId
					+ "' group by ASSIGN_ID";

		} else if (!cabId.equals("") && !cabId.equals("All") && !driverId.equals("")) {

			query = "SELECT * FROM assign_driver_cab where CAB_ID='" + cabId + "' and DRIVER_ID='" + driverId
					+ "' and CAB_OWNER_ID='" + userId + "' group by ASSIGN_ID";

		} else {

			query = "SELECT * FROM assign_driver_cab where CAB_OWNER_ID='" + userId + "' group by ASSIGN_ID";

		}

		Set<AssignDriverCab> driverSet = getDriverAssignedDetails(query);

		return driverSet;
	}

	public String getCabStatus(String cabId) {

		String status = null;

		PreparedStatement pst = null;

		try {
			String query = "SELECT STATUS FROM add_cab where CAB_REG_NO='" + cabId + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				status = rs.getString("STATUS");

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	public List<AddCab> searAdCab(String email, String fromDate1, String toDate1) {

		String query = null;
		String fromDate = "", toDate = "";

		if (fromDate1 != null && !fromDate1.equals("") && !fromDate1.equals("null")) {
			fromDate = new SQLDate().getSQLDate(fromDate1);
		}
		if (toDate1 != null && !toDate1.equals("") && !toDate1.equals("null")) {
			toDate = new SQLDate().getSQLDate(toDate1);
		}
		if (email != null && email.equals("null")) {
			email = null;
		}

		if (email != null && !email.equals("")) {

			query = "select * from add_cab where CAB_OWNER_ID='" + email + "'";

		} else if (email != null && !email.equals("") && !fromDate1.equals("") && !toDate1.equals("")) {

			query = "select * from add_cab where CAB_OWNER_ID='" + email + "' and CAB_ADDED_DATE between '" + fromDate
					+ "' and '" + toDate + "'";

		} else if ((email != null && email.equals("")) && !fromDate1.equals("") && !toDate1.equals("")) {

			query = "select * from add_cab where CAB_ADDED_DATE>='" + fromDate + "' and CAB_ADDED_DATE<='" + toDate
					+ "'";

		} else if ((email != null && email.equals("")) || (fromDate1 != null && !fromDate1.equals(""))
				|| (toDate1 != null && !toDate1.equals(""))) {

			query = "select * from add_cab where (CAB_OWNER_ID='" + email + "' and CAB_ADDED_DATE='" + fromDate
					+ "') or (CAB_OWNER_ID='" + email + "' and CAB_ADDED_DATE='" + toDate + "') or (CAB_OWNER_ID='"
					+ email + "') or (CAB_ADDED_DATE='" + fromDate + "') or (CAB_ADDED_DATE='" + toDate + "')";

		} else {

			query = "select * from add_cab where STATUS='INACTIVE'";

		}

		List<AddCab> cabList = getCabFullDetails1(query);

		return cabList;
	}

	public List<CabBook> searchTrCabBooking(String date1, String date2, String email) {

		if (date1 != null && !date1.equals("") && !date1.equals("null")) {
			date1 = new SQLDate().getSQLDate(date1);
		}
		if (date2 != null && !date2.equals("") && !date1.equals("null")) {
			date2 = new SQLDate().getSQLDate(date2);
		}

		String query = "";
		if (date1 != null && !date1.equals("") && date2 != null && !date2.equals("")) {

			query = query + " select * from cab_booking where  STR_TO_DATE(BOOKING_DATE, '%d-%m-%Y') between '" + date1
					+ "' and '" + date2 + "' and USER_EMAIL='" + email + "'";

		} else if ((date1 != null && !date1.equals("")) || (date2 != null && !date2.equals(""))) {

			query = query + "select * from cab_booking where (STR_TO_DATE(BOOKING_DATE, '%d-%m-%Y')='" + date1
					+ "' or STR_TO_DATE(BOOKING_DATE, '%d-%m-%Y')='" + date2 + "')  and USER_EMAIL='" + email + "'";

		} else {

			query = query + "select * from cab_booking where BOOKING_STATUS='BOOKED'  and USER_EMAIL='" + email + "'";
		}

		List<CabBook> cabBookingList = getBookingDetails(query);

		return cabBookingList;
	}

	public String addCabCancellationPrice(CabCancellationPolicyBean cancellationPolicy) {

		String msg = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;

		try {

			String query = "insert into cab_cancellation_policy values(?,?,?,?,CURDATE(),?,?)";
			String query1 = "update cab_cancellation_policy set CANCELLATION_CHARGES_STATUS='INACTIVE' where TIME='"
					+ cancellationPolicy.getTIME() + "' and CAB_OWNER_ID='" + cancellationPolicy.getCAB_OWNER_ID()
					+ "' and CAB_REG_NO='" + cancellationPolicy.getCAB_REG_NO() + "'";
			String query2 = "select CAB_REG_NO from cab_cancellation_policy where CAB_OWNER_ID='"
					+ cancellationPolicy.getCAB_OWNER_ID() + "' and TIME='" + cancellationPolicy.getTIME()
					+ "' and CAB_REG_NO='" + cancellationPolicy.getCAB_REG_NO() + "'";

			con = DBConnection.getConnection();
			con.setAutoCommit(false);

			pst = con.prepareStatement(query);
			pst1 = con.prepareStatement(query1);
			pst2 = con.prepareStatement(query2);

			pst.setInt(1, 0);
			pst.setString(2, cancellationPolicy.getCAB_REG_NO());
			pst.setString(3, cancellationPolicy.getTIME());
			pst.setString(4, cancellationPolicy.getCANCELLATION_CHARGES());
			pst.setString(5, "ACTIVE");
			pst.setString(6, cancellationPolicy.getCAB_OWNER_ID());

			ResultSet rs = pst2.executeQuery();
			String ownerId = null;

			while (rs.next()) {
				ownerId = rs.getString("CAB_REG_NO");
			}
			int j = pst1.executeUpdate();
			int i = pst.executeUpdate();
			if (ownerId == null) {

				if (i > 0) {
					con.commit();
					msg = "cancellation charges added successfully!.do you want to add one more time";

				}
			} else {

				if (i > 0 && j > 0) {
					con.commit();
					msg = "cancellation charges updated successfully!.";
				} else {
					con.rollback();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				pst1.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return msg;
	}

	public String addConfirmationDate(String bookingId, String time) {

		PreparedStatement pst = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {
			String query = "update cab_booking_confirmation_time set CONFIRM_TIME=CURTIME() where BOOKING_ID='"
					+ bookingId + "'";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			int i = pst.executeUpdate();

			if (i > 0) {

				message = "updated";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public List<CabCancellationPolicyBean> getCabCancellaionData(String cabRegNo, String ownerEmail) {

		List<CabCancellationPolicyBean> cabCancelation = new ArrayList<CabCancellationPolicyBean>();
		String msg = null;
		PreparedStatement pst = null;

		try {
			String query = "select * from cab_cancellation_policy where CAB_REG_NO='" + cabRegNo
					+ "' and CAB_OWNER_ID='" + ownerEmail + "' and CANCELLATION_CHARGES_STATUS='ACTIVE'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				CabCancellationPolicyBean cancelPolicy = new CabCancellationPolicyBean();

				cancelPolicy.setTIME(rs.getString("TIME"));

				cancelPolicy.setCANCELLATION_CHARGES(rs.getString("CANCELLATION_CHARGES"));

				cabCancelation.add(cancelPolicy);

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabCancelation;
	}

	public String checkServiceType(String date) {

		String serviceType = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_SERVICE_TYPE FROM shared_cab where CAB_SHARING_DATE='" + date + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				serviceType = rs.getString("CAB_SERVICE_TYPE");
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return serviceType;
	}

	public Set<String> getCabId(String email) {

		Set<String> cabSet = new TreeSet<String>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_ID FROM cab_booking where CAB_OWNER_ID='" + email + "' and CAB_ID is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				cabSet.add(rs.getString("CAB_ID"));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabSet;
	}

	public Set<String> getCOBookedCabIds(String email) {
		Set<String> cabSet = new TreeSet<String>();
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_ID FROM cab_booking where USER_EMAIL='" + email + "' and CAB_ID is not null";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				cabSet.add(rs.getString("CAB_ID"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabSet;
	}

	public String updatePrice(String routeId, String userId, String price, String pricePerKm) {

		PreparedStatement pst = null;
		String message = null;
//		String date1=date.getSQLDate(driver.getDOB());
		try {
			String query = "update cab_route set PRICE='" + price + "',PRICE_PER_KM='" + pricePerKm
					+ "' where ROUTE_GEN_ID='" + routeId + "'";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			int i = pst.executeUpdate();

			if (i > 0) {

				message = "Price Updated Successfully!.";

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return message;
	}

	public List<CabRoute> searchCabRoute(String userId, String cabId, String date) {

		String query = "";

		if (!"".equals(cabId) && "".equals(date)) {

			query = "select * from cab_route where CAB_OWNER_ID='" + userId + "' and CAB_ID='" + cabId + "'";

		} else if ("".equals(cabId) && !"".equals(date)) {

			query = "select * from cab_route where CAB_OWNER_ID='" + userId + "' and PICK_UP_DATE='" + date + "'";

		} else if (cabId != null && !cabId.equals("") && !date.equals("")) {

			query = "select * from cab_route where CAB_OWNER_ID='" + userId + "' and CAB_ID='" + cabId
					+ "' and PICK_UP_DATE='" + date + "'";

		} else {
			query = "select * from cab_route where CAB_OWNER_ID='" + userId + "'";
		}
//		System.out.println(query);
		List<CabRoute> routeList = getCabRouteDetails(query);

		return routeList;
	}

	public String insertRoutePrice(String routeId, String price, String pricepeerkm) {

		String msg = null;
		PreparedStatement pst = null;

		try {

			String query = "insert into route_price values(?,?,?,CURDATE(),CURTIME(),?)";

			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			pst.setInt(1, 0);
			pst.setString(2, price);
			pst.setString(3, pricepeerkm);
			pst.setString(4, routeId);

			int i = pst.executeUpdate();

			if (i > 0) {

				msg = "Inserted Successfully!...";

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return msg;
	}

	public String insertUpdateCab(AddCab addCab, InputStream rcDoc, InputStream insuranceDoc, InputStream cabPhoto,
			InputStream rcDoc1, InputStream insuranceDoc1, InputStream cabPhoto1) {

		String msg = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;

		try {
			// String id=new IdGen().getId("CAB_ID");
			// String cabId=new CabId().CabId(id);

			String query = "insert into update_cab values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURDATE(),?)";
			String query1 = "update add_cab set CAB_BRAND=?,CAB_MODEL=?,MODEL_YEAR=?,CURRENT_MILEAGE=?,FUEL_TYPE=?,BODY_TYPE=?,TRANSMISSION=?,KM_DRIVEN=?,NO_OF_PASSENGER=?,COLOR=?,INSURENCE_COMP_NAME=?,CERTIFIED_COMP_NAME=?,REGISTERED_YEAR=?,REGISTERED_CITY=?,REGISTERED_STATE=?,RC_DOC=?,INSURANCE_DOC=?,CAB_PHOTO=?,STATUS=?,MOBILE_NO=?,ADDRESS=?,CITY=?,STATE=?,DISTRICT=?,PINCODE=?,CAB_PLATE_STATUS=? where CAB_REG_NO='"
					+ addCab.getCAB_REG_NO() + "' and CAB_OWNER_ID='" + addCab.getCAB_OWNER_ID() + "'";

			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			pst = con.prepareStatement(query);
			pst1 = con.prepareStatement(query1);

			pst.setInt(1, addCab.getCAB_SEQ_ID());
			pst.setString(2, addCab.getCAB_REG_NO());
			pst.setString(3, addCab.getCAB_BRAND());
			pst.setString(4, addCab.getCAB_MODEL());
			pst.setString(5, addCab.getMODEL_YEAR());
			pst.setString(6, addCab.getCURRENT_MILEAGE());
			pst.setString(7, addCab.getFUEL_TYPE());
			pst.setString(8, addCab.getBODY_TYPE());
			pst.setString(9, addCab.getTRANSMISSION());
			pst.setString(10, addCab.getKM_DRIVEN());
			pst.setString(11, addCab.getNO_OF_PASSENGER());
			pst.setString(12, addCab.getCOLOR());
			pst.setString(13, addCab.getINSURENCE_COMP_NAME());
			pst.setString(14, addCab.getCERTIFIED_COMP_NAME());
			pst.setString(15, addCab.getREGISTERED_YEAR());
			pst.setString(16, addCab.getREGISTERED_CITY());
			pst.setString(17, addCab.getREGISTERED_STATE());

			pst.setBlob(18, rcDoc);
			pst.setBlob(19, insuranceDoc);
			pst.setBlob(20, cabPhoto);

			pst.setString(21, addCab.getCAB_OWNER_ID());
			pst.setString(22, "");
			pst.setString(23, "INACTIVE");
			pst.setString(24, addCab.getMOBILE_NO());
			pst.setString(25, addCab.getADDRESS());
			pst.setString(26, addCab.getCITY());
			pst.setString(27, addCab.getSTATE());
			pst.setString(28, addCab.getDISTRICT());
			pst.setString(29, addCab.getPINCODE());
			pst.setString(30, addCab.getCAB_PLATE_STATUS());

			pst1.setString(1, addCab.getCAB_BRAND());
			pst1.setString(2, addCab.getCAB_MODEL());
			pst1.setString(3, addCab.getMODEL_YEAR());
			pst1.setString(4, addCab.getCURRENT_MILEAGE());
			pst1.setString(5, addCab.getFUEL_TYPE());
			pst1.setString(6, addCab.getBODY_TYPE());
			pst1.setString(7, addCab.getTRANSMISSION());
			pst1.setString(8, addCab.getKM_DRIVEN());
			pst1.setString(9, addCab.getNO_OF_PASSENGER());
			pst1.setString(10, addCab.getCOLOR());
			pst1.setString(11, addCab.getINSURENCE_COMP_NAME());
			pst1.setString(12, addCab.getCERTIFIED_COMP_NAME());
			pst1.setString(13, addCab.getREGISTERED_YEAR());
			pst1.setString(14, addCab.getREGISTERED_CITY());
			pst1.setString(15, addCab.getREGISTERED_STATE());

			pst1.setBlob(16, rcDoc1);
			pst1.setBlob(17, insuranceDoc1);
			pst1.setBlob(18, cabPhoto1);

			// pst1.setString(22, cabId);
			pst1.setString(19, "INACTIVE");
			pst1.setString(20, addCab.getMOBILE_NO());
			pst1.setString(21, addCab.getADDRESS());
			pst1.setString(22, addCab.getCITY());
			pst1.setString(23, addCab.getSTATE());
			pst1.setString(24, addCab.getDISTRICT());
			pst1.setString(25, addCab.getPINCODE());
			pst1.setString(26, addCab.getCAB_PLATE_STATUS());

//			pst.setString(31, "Full Cab");

			int i = pst.executeUpdate();
			int j = pst1.executeUpdate();

			if (i > 0 && j > 0) {
				con.commit();
				msg = "Cab Updated Successfully..";

			} else {
				con.rollback();
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				pst.close();
				pst1.close();
				con.close();
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return msg;
	}

	public String getCabBookingReturnDate(String routeId, String travelDate) {

		String returnDate = null;
		PreparedStatement pst = null;

		try {
			String query = "SELECT RETURNED_DATE,CAB_BOOKING_SEQ_NO FROM cab_booking where CAB_ROUTE_ID='" + routeId
					+ "' and  TRAVELING_DATE='" + travelDate
					+ "' and (BOOKING_STATUS='CONFIRMED' || BOOKING_STATUS='COMPLETED')";
			// String query="SELECT rt.*,bk.* from cab_route rt inner join cab_booking bk on
			// rt.ROUTE_GEN_ID=bk.CAB_ROUTE_ID where rt.ROUTE_GEN_ID='"+routeId+"' and
			// rt.PICK_UP_DATE='"+travelDate+"' and (BOOKING_STATUS='CONFIRMED' ||
			// BOOKING_STATUS='COMPLETED')";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				returnDate = rs.getString("RETURNED_DATE");
			}
			System.out.println("return date -- " + returnDate);
			System.out.println("Query -- " + query);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return returnDate;
	}

	public CabBook getCabBookingDts(String cabId, String travelDate) {

		// String returnDate=getCabBookingReturnDate(routeId,travelDate);

		/*
		 * if(travelDate!=null && !travelDate.equals("") &&
		 * !travelDate.equals("EveryDay") && !travelDate.equals("null")){ travelDate=new
		 * SQLDate().getSQLDate(travelDate); }
		 *//*
			 * if(returnDate!=null && !returnDate.equals("") &&
			 * !travelDate.equals("EveryDay") && !returnDate.equals("null")){ returnDate=new
			 * SQLDate().getSQLDate(returnDate); }
			 */

		// String query="SELECT * FROM cab_booking where CAB_ROUTE_ID='"+routeId+"' and
		// STR_TO_DATE(TRAVELING_DATE, '%d-%m-%Y')>='"+travelDate+"' and
		// STR_TO_DATE(RETURNED_DATE, '%d-%m-%Y')<='"+returnDate+"' and
		// (BOOKING_STATUS='CONFIRMED' || BOOKING_STATUS='COMPLETED')";

		String query = "select * from cab_booking where CAB_ID='" + cabId + "' and str_to_date('" + travelDate
				+ "','%d-%m-%Y') between str_to_date(TRAVELING_DATE,'%d-%m-%Y') and str_to_date(RETURNED_DATE,'%d-%m-%Y') and (BOOKING_STATUS='CONFIRMED' || BOOKING_STATUS='COMPLETED') order by CAB_BOOKING_SEQ_NO desc limit 1";

		List<CabBook> bookingList = getBookingDetails(query);

		CabBook cabBook = null;
		if (bookingList != null && bookingList.size() > 0) {
			cabBook = bookingList.get(0);
		}
		// System.out.println(query);
		return cabBook;

	}

	public List<CabCancellationPolicyBean> viewCabCancellationPolicy(String email, String cabId) {

		List<CabCancellationPolicyBean> cabCancelation = new ArrayList<CabCancellationPolicyBean>();
		String msg = null;
		PreparedStatement pst = null;
		String query = "";
		try {
			if (cabId != null) {
				query = "select * from cab_cancellation_policy where CAB_OWNER_ID='" + email + "' and CAB_REG_NO='"
						+ cabId + "' and CANCELLATION_CHARGES_STATUS='ACTIVE'";

			} else {
				query = "select * from cab_cancellation_policy where CAB_OWNER_ID='" + email
						+ "' and CANCELLATION_CHARGES_STATUS='ACTIVE'";
			}
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				CabCancellationPolicyBean cancelPolicy = new CabCancellationPolicyBean();

				cancelPolicy.setTIME(rs.getString("TIME"));
				cancelPolicy.setCAB_REG_NO(rs.getString("CAB_REG_NO"));
				cancelPolicy.setCANCELLATION_CHARGES(rs.getString("CANCELLATION_CHARGES"));
				cancelPolicy.setWAITING_CHARGES(rs.getString("WAITING_CHARGES"));

				cabCancelation.add(cancelPolicy);

			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cabCancelation;
	}

	public String addTravellerAdvanceAmount(AdvanceAmount adamt) {
		String message = null;
		PreparedStatement pst = null;

		try {
			String query = "INSERT INTO `traveller_advance_amount` (`TAA_SEQ_ID`, `TRAVELLER_ID`, `AMOUNT_PAID`, `PAID_DATE`, `REFUND_DATE`, `PAYMENT_DONE_AGAINST`, `TRACKING_ID`, `BANK_REF_NO`, `BOOKING_ID`, `PAYMENT_STATUS`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			pst.setInt(1, adamt.getTAA_SEQ_ID());
			pst.setString(2, adamt.getTRAVELLER_ID());
			pst.setString(3, adamt.getAMOUNT_PAID());
			pst.setString(4, adamt.getPAID_DATE());
			pst.setString(5, "");
			pst.setString(6, adamt.getPAYMENT_DONE_AGAINST());
			pst.setString(7, adamt.getTRACKING_ID());
			pst.setString(8, adamt.getBANK_REF_NO());
			pst.setString(9, adamt.getBOOKING_ID());
			pst.setString(10, adamt.getPAYMENT_STATUS());
			int i = pst.executeUpdate();

			if (i > 0) {
				message = "Payment Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return message;
	}

	public List<CabBook> getCabOwnerBookedCabs(CabBook cabbook, int offset, int noOfRecords) {
		List<CabBook> al = new ArrayList<CabBook>();
		Statement pst = null;
		String query = "SELECT SQL_CALC_FOUND_ROWS * FROM cab_booking where USER_EMAIL = '" + cabbook.getUSER_EMAIL()
				+ "'";
		try {
			if (cabbook.getCAB_ID() != null) {
				query += " and CAB_ID = '" + cabbook.getCAB_ID() + "'";
			}

			if (cabbook.getBOOKING_STATUS() != null) {
				query += " and BOOKING_STATUS = '" + cabbook.getBOOKING_STATUS() + "'";
			}
			query += " order by booking_date limit " + offset + ", " + noOfRecords;
//			System.out.println(query);
			con = DBConnection.getConnection();
			pst = con.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while (rs.next()) {

				CabBook bookedcabs = new CabBook();

				bookedcabs.setBOOKING_DATE(rs.getString("BOOKING_DATE"));
				bookedcabs.setBOOKING_ID(rs.getString("BOOKING_ID"));
				bookedcabs.setBOOKING_STATUS(rs.getString("BOOKING_STATUS"));
				bookedcabs.setCAB_BOOKING_SEQ_NO(Integer.parseInt(rs.getString("CAB_BOOKING_SEQ_NO")));
				bookedcabs.setCAB_ID(rs.getString("CAB_ID"));
				bookedcabs.setCAB_OWNER_ID(rs.getString("CAB_OWNER_ID"));
				bookedcabs.setCAB_ROUTE_ID(rs.getString("CAB_ROUTE_ID"));
				bookedcabs.setCAB_SERVICE_TYPE(rs.getString("CAB_SERVICE_TYPE"));
				bookedcabs.setCONTACT_NO(rs.getString("CONTACT_NO"));
				bookedcabs.setDROPPING_ADDRESS(rs.getString("DROPPING_ADDRESS"));
				bookedcabs.setFROM_CITY(rs.getString("FROM_CITY"));
//				bookedcabs.setNAME(rs.getString("NAME"));
//				bookedcabs.setPASSWORD(rs.getString("PASSWORD"));
				bookedcabs.setPICK_UP_ADDRESS(rs.getString("PICK_UP_ADDRESS"));
				bookedcabs.setPICK_UP_TIME(rs.getString("PICK_UP_TIME"));
				bookedcabs.setRETURNED_DATE(rs.getString("RETURNED_DATE"));
				bookedcabs.setTO_CITY(rs.getString("TO_CITY"));
				bookedcabs.setTRAVELING_DATE(rs.getString("TRAVELING_DATE"));
				bookedcabs.setUSER_EMAIL(rs.getString("USER_EMAIL"));

				al.add(bookedcabs);
			}

			rs.close();
			rs = pst.executeQuery("SELECT FOUND_ROWS()");
			if (rs.next())
				this.noOfRecords = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return al;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	@Override
	public HashMap<String, Set<String>> getBodyTypeRegCabBrands() {

		HashMap<String, Set<String>> bodyTypeRegCabBrands = new HashMap<String, Set<String>>();
		Statement pst = null;
		String bodytype = "", temp = "", brand = "";
		Set<String> Sedanset = new HashSet<String>();
		Set<String> Muvset = new HashSet<String>();
		Set<String> Mpvset = new HashSet<String>();
		Set<String> Hatchbackset = new HashSet<String>();
		Set<String> Crossoverset = new HashSet<String>();
		Set<String> Coupeset = new HashSet<String>();
		Set<String> Convertibleset = new HashSet<String>();
		String query = "SELECT BODY_TYPE,BRAND FROM vehicle_type group by brand order by BODY_TYPE";
		// SELECT BODY_TYPE,CAB_BRAND FROM add_cab order by BODY_TYPE
		try {

			con = DBConnection.getConnection();
			pst = con.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while (rs.next()) {
				bodytype = rs.getString(1);
				if (bodytype.equalsIgnoreCase("hatchback")) {
					Hatchbackset.add(rs.getString(2));
				} else if (bodytype.equalsIgnoreCase("sedan")) {
					Hatchbackset.add(rs.getString(2));
				} else if (bodytype.equalsIgnoreCase("muv/suv")) {
					Hatchbackset.add(rs.getString(2));
				} else if (bodytype.equalsIgnoreCase("mpv")) {
					Hatchbackset.add(rs.getString(2));
				} else if (bodytype.equalsIgnoreCase("crossover")) {
					Hatchbackset.add(rs.getString(2));
				} else if (bodytype.equalsIgnoreCase("coupe")) {
					Hatchbackset.add(rs.getString(2));
				} else if (bodytype.equalsIgnoreCase("convertible")) {
					Hatchbackset.add(rs.getString(2));
				}

			}
			bodyTypeRegCabBrands.put("Hatchback", Hatchbackset);
			bodyTypeRegCabBrands.put("Sedan", Hatchbackset);
			bodyTypeRegCabBrands.put("MUV/SUV", Hatchbackset);
			bodyTypeRegCabBrands.put("MPV", Hatchbackset);
			bodyTypeRegCabBrands.put("Crossover", Hatchbackset);
			bodyTypeRegCabBrands.put("Coupe", Hatchbackset);
			bodyTypeRegCabBrands.put("Convertible", Hatchbackset);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bodyTypeRegCabBrands;
	}

	@Override
	public String sendCabRequest(CabRequester cabRequester) {

		// SELECT r.email_id,r.CANDIDATE_NAME,r.contact_no FROM registration r,
		// (SELECT CAB_OWNER_ID FROM add_cab WHERE
		// BODY_TYPE='"+cabRequester.getBODY_TYPE()+"' AND CAB_BRAND in
		// ('"+cabRequester.getINTERESTED_BRANDS()+"')) ac,
		// (SELECT CAB_OWNER_ID FROM cab_route WHERE SERVICE_TYPE =
		// '"+cabRequester.getSERVICE_TYPE()+"') cr WHERE r.EMAIL_ID =ac.CAB_OWNER_ID
		// AND ac.CAB_OWNER_ID=cr.CAB_OWNER_ID group by r.reg_id

		String query = "SELECT r.email_id,r.CANDIDATE_NAME,r.contact_no,ac.CAB_OWNER_ID,ac.CAB_BRAND FROM registration r inner join add_cab ac on r.email_id=ac.cab_owner_id inner join cab_route cr on cr.cab_owner_id=ac.cab_owner_id where r.status = 'ACTIVE'";
		String mailids = "", contactnos = "", names = "", requestId = "", mailid[], contactno[], name[], message = "";
		AddCab ac = new AddCab();
		SmsCallGet smsCallGet = new SmsCallGet();
		Statement st = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			if (cabRequester.getBODY_TYPE() != null) {
				query += " and ac.BODY_TYPE='" + cabRequester.getBODY_TYPE() + "'";
			}

			if (cabRequester.getSERVICE_TYPE() != null) {
				query += " and cr.SERVICE_TYPE = '" + cabRequester.getSERVICE_TYPE() + "'";
			}

			if (cabRequester.getINTERESTED_BRANDS() != null) {
				query += " or ac.CAB_BRAND in ('" + cabRequester.getINTERESTED_BRANDS().replaceAll(",", "','") + "')";
			}

			query += " group by ac.cab_owner_id";
			System.out.println(query);
			con = DBConnection.getConnection();
			st = con.createStatement();

			rs = st.executeQuery(query);
			if (rs.next()) {
				do {

					if (rs.last()) {
						mailids += rs.getString(1);
						contactnos += rs.getString(2);
						names += rs.getString(3);
					} else {
						mailids += rs.getString(1) + ",";
						contactnos += rs.getString(2);
						names += rs.getString(3);
					}
				} while (rs.next());

				requestId = new IdGen().getId("CAB_REQUEST_ID");
				pst = con.prepareStatement(
						"INSERT INTO `cab_request_details` (`REQUEST_ID`, `REQUESTER_ID`, `SERVICE_TYPE`, `BODY_TYPE`, `DATE_REQUESTED`, `TRAVEL_DATE`,"
								+ " `INTERESTED_BRANDS`, `INTERESTED_PACKAGE`, `REQUEST_SENT_TO`, `FROM_LOCATION`, `TO_LOCATION`, `REQUEST_STATUS`, `PICK_UP_LOCATION`) VALUES (?, ?, ?, ?, sysdate(), ?, ?, ?, ?, ?, ?, ?, ?)");

				pst.setString(1, requestId);
				pst.setString(2, cabRequester.getREQUESTER_ID());
				pst.setString(3, cabRequester.getSERVICE_TYPE());
				pst.setString(4, cabRequester.getBODY_TYPE());
				pst.setString(5, cabRequester.getTRAVEL_DATE());
				pst.setString(6, cabRequester.getINTERESTED_BRANDS());
				pst.setString(7, cabRequester.getINTERESTED_PACKAGE());
				pst.setString(8, mailids);
				pst.setString(9, cabRequester.getFROM_LOCATION());
				pst.setString(10, cabRequester.getTO_LOCATION());
				pst.setString(11, "RAISED");
				pst.setString(12, cabRequester.getPICK_UP_LOCATION());
				pst.executeUpdate();
				// new BookingMail().cabRequestFromCustomer(cabRequester);
				mailid = mailids.split(",");
				contactno = contactnos.split(",");
				name = names.split(",");
				for (int i = 0; i < mailid.length; i++) {
					System.out.println("mesage sent to" + contactno[i]);
					// smsCallGet.sendCabRequestMessage(contactno[i], name[i], mailid[i]);
				}
				message = "Request sent. You will get response shortly.";
			} else {
				message = "No Cabs Available.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return message;
	}

	@Override
	public List<CabRequester> searchTravellerCabRequests(CabRequester cabrequester, int limit, int offset) {

		List<CabRequester> al = new ArrayList<CabRequester>();
		Statement st = null;
		ResultSet rs = null;
		String query = "SELECT SQL_CALC_FOUND_ROWS * FROM cab_request_details where SNO is not null";

		if (cabrequester.getFROM_LOCATION() != null) {
			if (cabrequester.getFROM_LOCATION() != "") {
				query += " and FROM_LOCATION = '" + cabrequester.getFROM_LOCATION() + "'";
			}
		}
		if (cabrequester.getTO_LOCATION() != null) {
			if (cabrequester.getTO_LOCATION() != "") {
				query += " and TO_LOCATION = '" + cabrequester.getTO_LOCATION() + "'";
			}
		}
		if (cabrequester.getTRAVEL_DATE() != null) {
			if (cabrequester.getTRAVEL_DATE() != "") {
				query += " and TRAVEL_DATE = '" + new SQLDate().getSQLDate(cabrequester.getTRAVEL_DATE()) + "'";
			}
		}

		query += " order by DATE_REQUESTED desc limit " + limit + ", " + offset;
//		System.out.println(query);
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				CabRequester cr = new CabRequester();

				cr.setSNO(rs.getInt("SNO"));
				cr.setREQUEST_ID(rs.getString("REQUEST_ID"));
				cr.setREQUESTER_ID(rs.getString("REQUESTER_ID"));
				cr.setSERVICE_TYPE(rs.getString("SERVICE_TYPE"));
				cr.setBODY_TYPE(rs.getString("BODY_TYPE"));
				cr.setDATE_REQUESTED(new SQLDate().getInDate(rs.getString("DATE_REQUESTED")));
				cr.setTRAVEL_DATE(new SQLDate().getInDate(rs.getString("TRAVEL_DATE")));
				cr.setINTERESTED_BRANDS(rs.getString("INTERESTED_BRANDS"));
				cr.setINTERESTED_PACKAGE(rs.getString("INTERESTED_PACKAGE"));
				cr.setREQUEST_SENT_TO(rs.getString("REQUEST_SENT_TO"));
				cr.setFROM_LOCATION(rs.getString("FROM_LOCATION"));
				cr.setTO_LOCATION(rs.getString("TO_LOCATION"));
				cr.setREQUEST_STATUS(rs.getString("REQUEST_STATUS"));
				al.add(cr);
			}

			rs.close();
			rs = st.executeQuery("SELECT FOUND_ROWS()");
			if (rs.next())
				this.noOfRecords = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	@Override
	public List<String> getCarBrands() {
		ArrayList<String> al = new ArrayList<>();
		String query = "SELECT BRAND FROM vehicle_type group by brand";
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
//		    	System.out.println(rs.getString("BRAND"));
				al.add(rs.getString("BRAND"));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return al;
	}

	@Override
	public List<String> getCarColors() {

		ArrayList<String> al = new ArrayList<>();
		String query = "SELECT color FROM add_cab group by color";
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
//		    	System.out.println(rs.getString("COLOR"));
				al.add(rs.getString("COLOR"));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return al;
	}

	// Code Added By Virendra
	@Override
	public int isCab_PlanActive(String cab_id) {
		int returnFlag = 0;

		Statement st = null;
		ResultSet rs = null;
		try {
			/*
			 * Date date = new Date(); SimpleDateFormat formatter = new
			 * SimpleDateFormat("dd/MM/yyyy"); String strDate = formatter.format(date);
			 */
			// String query="SELECT * FROM add_cab where plan_expiry_date<'"+strDate+"' and
			// CAB_REG_NO='"+cab_id+"'";

//				String query="SELECT * FROM add_cab where CAB_REG_NO='"+cab_id+"'";
			AddCab cab = getCabDetails(cab_id);
			String query = "SELECT * FROM cab_membership_request_details where add_cab_id='" + cab.getCAB_SEQ_ID()
					+ "'";

			con = DBConnection.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			boolean size = rs.next();
			if (size) {
				Date currentDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String expiryDate = rs.getString("plan_expiry_date");
				long difference_In_Days = 0;

				if (expiryDate != null) {
					Date date1 = sdf.parse(expiryDate);
					long difference_In_Time = date1.getTime() - currentDate.getTime();
					difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24));
				} else {
					returnFlag = 1;
				}
				if (difference_In_Days < 0) {
					returnFlag = 1;
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnFlag; // if return 1 then disable the button
	}
	// Ends Code Added By Virendra

	// code added by arti
	public int getCabSEQID(String cabRegNo) {
		int seqId = 0;
		PreparedStatement pst = null;

		try {
			String query = "SELECT CAB_SEQ_ID FROM add_cab where CAB_REG_NO='" + cabRegNo + "'";
			con = DBConnection.getConnection();
			pst = con.prepareStatement(query, pst.RETURN_GENERATED_KEYS);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				seqId = rs.getInt("CAB_SEQ_ID");

			}

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return seqId;

	}

	@Override
	public String addCabRoutes(CabRoute cabRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateCabRoutes(CabRoute cabRoute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addDriver(DriverBean driver, InputStream doc, InputStream photo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateDriver(DriverBean driver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DriverBean getDriverNames(String driverId, String cabId) {
		// TODO Auto-generated method stub
		return null;
	}

	// end code by arti
}
