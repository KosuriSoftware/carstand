   package com.zoro.utilities;

	import java.text.DateFormat;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;

	public class SQLDate {

		private SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");

		public String getSQLDate(String date)
		{
			try {
				java.util.Date ddate=sdf.parse(date);
				date=sdf1.format(ddate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
		
		public String getInDate(String date)
		{
			try {
				java.util.Date ddate=sdf1.parse(date);
				date=sdf.format(ddate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
		
		public String getSysDate()
		{
			Date date;
			String dat="";
			String datereg="";
			try {
				date = Calendar.getInstance().getTime();
		        String today = sdf.format(date);
	//	        System.out.println("Today "+today);
		        datereg=sdf1.format(date);
	//	        System.out.println("Date Reg "+datereg);
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return datereg;
		}
		
		public String getTime(String hrs,String mins,String meridian)
		{
			Date date = null;
			int hr=Integer.parseInt(hrs);
			if(meridian.equals("PM"))
			{
				hr+=12;
			}
			String time=hr+":"+mins;
			try {
			    date = new SimpleDateFormat("HH:mm").parse(time);
			    
			    System.out.println(date);
			    
			}
			catch (Exception e) {
			    e.printStackTrace();
			}
			System.out.println(time);
			return time;
		}
		
		public String convertTo12Hour(String Time) {
		    
			String x=null;
			try {
			    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			    final Date dateObj = sdf.parse(Time);
			    x=new SimpleDateFormat("K:mm a").format(dateObj);
			    if(x.equals("0:00 AM") || x.equals("0:00 PM"))
			    {
			    	x="12:00 "+new SimpleDateFormat("a").format(dateObj);;
			    }
			} catch (final ParseException e) {
			    e.printStackTrace();
			}

		    return x;
		}
			
	}

