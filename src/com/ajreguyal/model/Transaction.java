package com.ajreguyal.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	private String time;
	private String date;
	private String buyer;
	private String seller;
	private long volume;
	private float price;
	private long timeId;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	public String getBuyer() {
		return buyer;
	}
	
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public String getSeller() {
		return seller;
	}
	
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public long getVolume() {
		return volume;
	}
	
	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public long getTimeId() {
		return createTimeId(date, time);
	}
	
	public void setTimeId(long timeId) {
		this.timeId = timeId;
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
	
	@Override
	public String toString() {
		return new StringBuilder().append("Id = ").append(this.getTimeId())
				.append(".Buyer = ").append(this.buyer)
				.append(".Seller = ").append(this.seller)
				.append(". Price = ").append(this.price)
				.append(".Volume = ").append(this.volume)
				.append("\n").toString();
	}
}
