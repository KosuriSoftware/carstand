package com.zoro.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class IdGen {
	private static Connection con=null;
	public String getId(String message)
	{
			int id=0;
			String query="SELECT id FROM idgenerator where name='" + message + "'";
			String query1="update idgenerator set id=? where name=?";
			try {
				con=DBConnection.getConnection();
				Statement st=con.createStatement();
				synchronized (this) {
					ResultSet rs = st.executeQuery(query);
					if (rs.next()) {
						id = rs.getInt(1);
					}
					PreparedStatement pst=con.prepareStatement(query1);
					id=id+1;
					Statement sst=con.createStatement();
					int kk=sst.executeUpdate("update idgenerator set id='"+id+"' where name='"+message+"'");
				}
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return String.valueOf(id);
		}
}
