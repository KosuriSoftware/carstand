package com.zoro.utilities;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.zoro.dao.LoginDAO;
import com.zoro.dao.impl.LoginDAOImpl;
import com.zoro.mailing.utilities.VerificationMail;
import com.zoro.sms.utilities.OtpGenerator;
import com.zoro.sms.utilities.SendTLSms;

public class LoginUtilities {

	private LoginUtilities() {

	}

	public static String[] verifyEmailAndMobilePattern(String emailId, String mobileNo) {

		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		final String MOBILE_PATTERN = "\\d{10}";
		String[] emailAndMobileStrArr = new String[3];
		String msg = null;

		if (emailId != null) {

			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(emailId);
			Pattern pattern1 = Pattern.compile(MOBILE_PATTERN);
			Matcher matcher1 = pattern1.matcher(mobileNo); // added

			if (matcher.matches()) {
				emailAndMobileStrArr[0] = emailId;
				System.out.println("email is :" + emailAndMobileStrArr[0]);
			} else if (matcher1.matches()) {
				emailAndMobileStrArr[1] = mobileNo;
				System.out.println("Mobile is :" + emailAndMobileStrArr[1]);
			}
		} else {
			msg = "user id should not be empty!.";
			emailAndMobileStrArr[3] = msg;
		}

		return emailAndMobileStrArr;

	}

	public static String loginStatusActiveYN(Map<String, String> map, String[] emailAndMobileStrArr, LoginDAO loginDAO,
			String password) {

		String loginStatusMsg = null;
		String email = emailAndMobileStrArr[0];
		String mobileNo = emailAndMobileStrArr[1];

		if (email != null && map.get("regStatus") != null && map.get("regStatus").equals("INACTIVE")) {
			try {
				new VerificationMail().verifyMail(map.get("email"));
			} catch (Exception e) {

			}
			
			loginStatusMsg = "Your EMail-ID not yet Verified, A Verification link has been sent to your EMail-ID. Please Verify your EMail-ID!.";

		} else if (mobileNo != null && map.get("mobileStatus") != null && map.get("mobileStatus").equals("INACTIVE")) {
			char[] otpgen = new OtpGenerator().OTP(6);
			String otps = String.valueOf(otpgen);

			String msg2 = otps + " "
					+ "is OTP to verify your registered mobile number and valid for 30 mins - www.carstand.in";
			System.out.println(msg2);

			new SendTLSms().sendSms(msg2, mobileNo);
			loginDAO.updateMobileOtp(otps, mobileNo);
			loginStatusMsg = "mobile";
		} else if (mobileNo != null && map.get("loginStatus") != null && map.get("loginStatus").equals("BLOCK")) {
			loginStatusMsg = "Admin Blocked You!.";
		} else if (mobileNo != null && map.get("psw") != null && !map.get("psw").equals(password)) {
			loginStatusMsg = "please enter correct password!.";
		} else if (email != null && map.get("psw") != null && !map.get("psw").equals(password)) {
			loginStatusMsg = "please enter correct password!.";
		} else if (mobileNo != null && map.get("loginStatus") == null) {
			loginStatusMsg = "please enter correct user id!.";
		} else if (mobileNo != null && map.get("loginStatus") != null && map.get("loginStatus").equals("ACTIVE")
				&& map.get("psw").equals(password)) {

				if (map.get("usertype").equals("T") || map.get("usertype").equals("A") 	|| map.get("usertype").equals("AE")) {
					loginStatusMsg = "all verified";
				} else {
					loginStatusMsg = "all verified";
				}
		} else if (email != null && map.get("loginStatus") != null && map.get("loginStatus").equals("ACTIVE")
				&& map.get("psw").equals(password)) {

			if (map.get("usertype").equals("T") || map.get("usertype").equals("A")
					|| map.get("usertype").equals("AE")) {
							loginStatusMsg = "all verified";
			} else {
				loginStatusMsg = "all verified";
			}
		}
		return loginStatusMsg;
	}

	
	public static String getUserEmailId(String emailId) {
		return new LoginDAOImpl().getUserEmail(emailId);
	}
	
	public static Map<String, String> getMobileVerificationDetails(String mobileNo) {
		return new LoginDAOImpl().getMobileVerificationDetails(mobileNo);
	}
}
