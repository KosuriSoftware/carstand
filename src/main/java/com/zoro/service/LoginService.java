package com.zoro.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoro.dao.LoginDAO;
import com.zoro.dao.impl.LoginDAOImpl;
import com.zoro.dto.LoginRequest;
import com.zoro.utilities.LoginUtilities;

@Service
public class LoginService {

	@Autowired
	LoginDAO loginDAO;

	public String login(LoginRequest loginRequest) {

		String msg = null;
		String[] emailAndMobileStrArr = LoginUtilities.verifyEmailAndMobilePattern(loginRequest.getEmailId(),
				loginRequest.getContactNo());

		Map<String, String> map = loginDAO.loginCheck(emailAndMobileStrArr[0], emailAndMobileStrArr[1]);
		msg = LoginUtilities.loginStatusActiveYN(map, emailAndMobileStrArr, loginDAO, loginRequest.getPassword());
		String userId = LoginUtilities.getUserEmailId(emailAndMobileStrArr[0]);

		loginDAO.testJdbcTemplate();
		
		String msg1 = null;

		switch (msg) {

		case "mobile":
			msg1 = "Your Mobile No. not yet Verified. OTP has been sent to your registered Mobile No.";
			Map<String, String> mobMap = LoginUtilities.getMobileVerificationDetails(emailAndMobileStrArr[0]);
			System.out.println("./login.jsp?msg1=" + msg1 + "&mobileNo=" + mobMap.get("mobileNo") + "&email="
					+ mobMap.get("email"));
			// response.sendRedirect("./login.jsp?msg1="+msg1+"&mobileNo="+mobMap.get("mobileNo")+"&email="+mobMap.get("email"));
			break;

		case "all verified":
			msg1 = msg;
			// session.setAttribute("userId", userId);
			// response.sendRedirect("./index.jsp?msg="+msg1);
			break;

		default:
			msg1 = msg;
			// response.sendRedirect("./login.jsp?msg1="+msg);
			System.out.println("msg1" + msg1);
			break;

		}

		return msg;
	}

}
