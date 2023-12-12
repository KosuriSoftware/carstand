package com.zoro.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	
private static Connection con=null;
	
	public static Connection getConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			con=DriverManager.getConnection("jdbc:mysql://65.0.167.251/new_carstand_mobile_db?useSSL=false","kslcarstand","carstand@23");/*old zorocabs.cnj2sq1hvhgw.ap-south-1.rds.amazonaws.com */			
//			con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/carstand?useSSL=false","root","root");/*old zorocabs.cnj2sq1hvhgw.ap-south-1.rds.amazonaws.com */
					//con=DriverManager.getConnection("jdbc:mysql://localhost/carstand?useSSL=false","root","root");/*old zorocabs.cnj2sq1hvhgw.ap-south-1.rds.amazonaws.com */
			    con=DriverManager.getConnection("jdbc:mysql://65.0.167.251/carstand?useSSL=false","kslcarstand","carstand@23");
//			con=DriverManager.getConnection("jdbc:mysql://zorocabs.cmrhzvst1vkt.us-east-1.rds.amazonaws.com:3306/zorocabs","zorocabs","ZOROCABSksl");
			//System.out.println("Connection established...");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
		}
	
	/*public static void main(String args[])
	{
		getConnection();
	}*/
}

