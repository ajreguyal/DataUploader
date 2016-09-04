package com.ajreguyal.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ajreguyal.time.TimeFrame;

public class Transaction {
	private String time;
	private String date;
	private String buyer;
	private String seller;
	private long volume;
	private float price;
	private long timeId;
	private TimeFrame timeFrameManipulator;
	
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
		return timeFrameManipulator.createTimeId(date, time);
	}
	
	public void setTimeId(long timeId) {
		this.timeId = timeId;
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
	
	public static void main(String args[]) {
//		System.out.println(createTimeId("2016/09/01", "9:30:00 AM"));
//		System.out.println(createTimeId("2016/09/01", "9:31:00 AM"));
	}

	public void setTimeFrameManipulator(TimeFrame timeFrameManipulator) {
		this.timeFrameManipulator = timeFrameManipulator;
	}
}
