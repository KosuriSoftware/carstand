package com.zoro.sms.utilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SmsCallGet {



	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		SmsCallGet test = new SmsCallGet();
//		test.sendMessage("8106212590","saritakumari0802@gmail.com");
//	}
	public void sendMessage(String mobile,String name,String emailid) {

		try {

			final TrustManager[] trustAllCerts = new TrustManager[] { 
					new X509TrustManager() 
					{
						
						public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
						}
						
						public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
						}
						
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					} };
			


			final SSLContext sslContext = SSLContext.getInstance( "SSL" );
			sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );

			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			HttpsURLConnection.setDefaultSSLSocketFactory( sslContext.getSocketFactory() );
			URL url = new URL(getURLPath(mobile,name,emailid));
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();
//			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
//			System.out.println(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendCabRequestMessage(String mobile,String name,String emailid) {

		try {

			final TrustManager[] trustAllCerts = new TrustManager[] { 
					new X509TrustManager() 
					{
						
						public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
						}
						
						public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
						}
						
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					} };
			


			final SSLContext sslContext = SSLContext.getInstance( "SSL" );
			sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );

			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			HttpsURLConnection.setDefaultSSLSocketFactory( sslContext.getSocketFactory() );
			URL url = new URL(getURLPath2(mobile,name,emailid));
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();
//			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
//			System.out.println(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private String getURLPath(String mobile,String name, String emailid) {
//		String twar = getURL()+"httpapi/send?username=maheshbabu.i@kosurisoft.com&password=mahesh&sender_id=SMSIND&route=T&phonenumber="+mobile+"&message=Dear%20%23"+name+"%23%2C%20Thanks%20for%20Registering%20with%20Campusguide.in.%20Verification%20mail%20has%20been%20sent%20to%20your%20emailID%20%23"+emailid+"%23.";
		String twar= getURL()+"httpapi/send?username=contact@zorocabs.com&password=zorocabs&sender_id=SMSIND&route=T&phonenumber="+mobile+"&message=Dear%20%23"+name+"%23%2CThank%20you%20for%20Registering%20with%20zorocabs.com.%20Your%20OTP%20For%20Mobile%20verification%20is%20%23"+emailid+"%23.%20Please%20do%20not%20share%20with%20others.";
		return twar;
	}

	private String getURLPath1(String mobile,String name, String emailid) {
//		String twar = getURL()+"httpapi/send?username=maheshbabu.i@kosurisoft.com&password=mahesh&sender_id=SMSIND&route=T&phonenumber="+mobile+"&message=Dear%20%23"+name+"%23%2C%20Thanks%20for%20Registering%20with%20Campusguide.in.%20Verification%20mail%20has%20been%20sent%20to%20your%20emailID%20%23"+emailid+"%23";
		
		String sms="Dear "+name+" Thank you for Registering to zorocabs, your one time password is "+emailid+" for your mobile verification,please don't share this otp to anyone!. ";
		
		String twar= getURL()+"httpapi/send?username=contact@zorocabs.com&password=zorocabs&sender_id=SMSIND&route=T&phonenumber="+mobile+"&message=%23"+sms+"%23";
		return twar;
	}
	private String getURLPath2(String mobile,String name, String emailid) {
		String twar= getURL()+"httpapi/send?username=contact@zorocabs.com&password=zorocabs&sender_id=SMSIND&route=T&phonenumber="+mobile+"&message=Dear%20%23"+name+"%23%2CYou%20have%20a%20New%20Cab%20Request.%20Please%20Check%20Your%20email%20%23"+emailid+"%23.";
		return twar;
	}
	
	private String getURL() {

		return "http://securesmsc.com/";
	}
	
	/*public static void main(String[] ar){
		
		new SmsCallGet().sendMessage("8106212590", "sari", "8e7re");
		System.out.println("sdjkfhdjfgdfhd");
	}*/
}

