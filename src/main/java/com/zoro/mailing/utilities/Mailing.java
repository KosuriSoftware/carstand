package com.zoro.mailing.utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailing {
	
	
	public  void send(String userEmail,String msg){
		/*public static void send(String to, String userEmail,String msg){*/
		
//		final String username="info@kosuriauto.com";//change accordingly  
//		final String password="cskam2021@";
//     	final String username="carstandcarstand2@gmail.com";//change by danish 
//     	final String password="xcjgzcunmqyswwrq";
     	
		final String username="carstd@kosuriauto.com";//change by arti 	
		final String password="CarStd23@";
		
		
		/*final String username="rxkolan@gmail.com";//change accordingly  
		final String password="rxksl2020@";*/
		
//		final String senderEmailId="info@kosuriauto.com";
		final String senderEmailId="carstd@kosuriauto.com";
//		final String senderEmailId="carstandcarstand2@gmail.com";
		
		MimeBodyPart mimeBodyPart=new MimeBodyPart();
		  MimeMultipart multipart=new MimeMultipart();
	     try {
			mimeBodyPart.setContent(msg,"text/html");
		    multipart.addBodyPart(mimeBodyPart);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	
	 	String host = "smtp.gmail.com"; // gmail smtp host name
	 	

	    // String host = "smtp8.net4india.com"; // net4 smtp host name
	 	Properties props = new Properties();
	 	props.put("mail.smtp.host", host);//change accordingly  
	 	props.put("mail.smtp.port", "587");
	 	props.put("mail.smtp.auth", "true");  
	 	props.put("mail.smtp.starttls.enable", "true");
	 	props.put("mail.smtp.ssl.protocols", "TLSv1.2");  //added by arti
	 	//my changes 
	 	props.put("mail.user", username);
	 	props.put("mail.password", password);
	// 	props.put("mail.debug", "true");
	 	
	 	EMailAuthenticator auth=new EMailAuthenticator(username,password);

	 	Session session = Session.getDefaultInstance(props, auth);  
	 	//2nd step)compose message  
	 	try {  
	 	 MimeMessage message = new MimeMessage(session);  
	 	message.setFrom(new InternetAddress(senderEmailId));  
	 	// message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));  
	 	 
	 	message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));
	 	message.setSubject("Welcome to carstand.in");
	 	 message.setSentDate(new java.util.Date());
	 	// message.setText(msg);  
	 	message.setText(msg,"UTF-8","html");  
	 	
	 	/*Transport transport = session.getTransport("smtp");
	      transport.connect(host, username, password);
	      transport.sendMessage(message, message.getAllRecipients());
	
	      transport.close();*/
	      System.out.println("Sent message successfully....");
	 	 //3rd step)send message  
	 	 Transport.send(message);  
	 	  
	 	 //System.out.println("Done");  
	 	  
	 	 } catch (MessagingException e) {  
	 	    throw new RuntimeException(e);  
	 	 }  

		}
	
	/*public static void main(String args[])
	{
		new Mailing().send("rxkolan@gmail.com","kosuri");
	} 
	*/

}
