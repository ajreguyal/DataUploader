package com.ajreguyal.time;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFrame {


	public long getNextTime(long time, int timeFrame) {
		return time + (timeFrame * 60000);
	}
	
	public String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("HH:mm:ss a");
	    return format.format(date);
	}
	
	public String convertDate(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("MM/dd/yyyy");
	    return format.format(date);
	}

	public long createTimeId(String date, String time) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
		Date d;
		try {
			d = dateFormat.parse(date + " " + time);
			return d.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
