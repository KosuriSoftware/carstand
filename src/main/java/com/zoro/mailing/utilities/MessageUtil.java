package com.zoro.mailing.utilities;

public class MessageUtil {
	
	public static String getRegistrationOTPMessage(String OTP) {
		/*Old Msg
		Dear customer, Thank you for Registering to zorocabs, Your One Time Password is %s for your mobile verification,please don't share this otp to anyone!.
		 */		
		return String.format("Thank you for registering with carstand.in. OTP for verify your Mobile Number is", OTP);
	}

	
	public static String getForgotPwdMessage(String OTP) {
		return String.format("Dear customer, Enter your one time password is to change password,please don't share this otp to anyone!. ", OTP);
	}
	
	public static String getBookingRequest(String OTP) {
		String bookingTxt = "The requested Cab BOOKING details are%n" +
				"Request ID: %%|RequestId^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" +  
				"Cab ID: %%|CabId^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"Phone : %%|MobileNumber^{\"inputtype\" : \"text\", \"maxlength\" : \"12\"}%%\r\n" + 
				"From : %%|From City^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"To: %%|To Cityl^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"TravelDate: %%|TravelDatel^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"ReturDate: %%|TravelReturn^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%";
		return String.format("Dear customer, Your cab booking request details" );
	}
		
    public static String getConfirmCabDetails(String OTP) {
    	
    	String bookingTxt = "Your  Cab BOOKING confirmed. details are%n" +
    			"Booking ID: %%|BookingId^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" +  
				"Cab ID: %%|CabId^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"Phone : %%|MobileNumber^{\"inputtype\" : \"text\", \"maxlength\" : \"12\"}%%\r\n" + 
				"From : %%|From City^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"To: %%|To Cityl^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"TravelDate: %%|TravelDatel^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%\r\n" + 
				"ReturDate: %%|TravelReturn^{\"inputtype\" : \"text\", \"maxlength\" : \"100\"}%%";
			return String.format("Dear customer, Your cab booking details ");
    }
    
    public static String getDriverDetails(String OTP) {
		return String.format("Dear customer, Your Driver Details");
}
			
    }


