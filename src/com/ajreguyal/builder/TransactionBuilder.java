package com.ajreguyal.builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.ajreguyal.model.Transaction;

public class TransactionBuilder {
	private String buyer;
	private String seller;
	private long volume;
	private float price;
	private String date;
	private String time;
	
	public TransactionBuilder() {
		
	}
	
	public TransactionBuilder setBuyer(String buyer) {
		this.buyer = buyer;
		return this;
	}
	
	public TransactionBuilder setSeller(String seller) {
		this.seller = seller;
		return this;
	}
	
	public TransactionBuilder setVolume(String volume) {
		try {
			this.volume = Long.parseLong(volume.replace(",", ""));
		} catch (NumberFormatException ex) {
			System.out.println("invalid volume: " + volume);
		}
		return this;
	}
	
	public TransactionBuilder setPrice(String price) {
		try {
			this.price = Float.parseFloat(price.replace(",", ""));
		} catch (NumberFormatException ex) {
			System.out.println("invalid price: " + price);
		}
		return this;
	}
	
	public TransactionBuilder setDate(String date) {
		this.date = date;
		return this;
	}
	
	public TransactionBuilder setTime(String time) {
		this.time = time;
		return this;
	}
	
	public Transaction create() throws ParseException {
		Transaction transaction = null;
		if (isAllDataValid()) {
			transaction = new Transaction();
			transaction.setTime(time);
			transaction.setDate(date);
//			transaction.setTimeId(createTimeId(this.date, this.time));
			transaction.setBuyer(buyer);
			transaction.setSeller(seller);
			transaction.setPrice(price);
			transaction.setVolume(volume);
		}
		return transaction;
	}

	private boolean isAllDataValid() {
		return (this.date != null)
				&& (this.time != null)
				&& (this.buyer != null)
				&& (this.seller != null)
				&& (this.volume > 0)
				&& (this.price > 0);
	}
	
	public static void main(String[] args) {
//		try {
//			String id = String.valueOf(createTimeId("2016/07/06", "9:30:59 AM"));
//			System.out.println(id);
//			String id2 = String.valueOf(createTimeId("2016/07/06", "9:30:59 PM"));
//			System.out.println(id2);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void clear() {
		this.time = null;
		this.buyer = null;
		this.seller = null;
		this.price = 0;
		this.volume = 0;
	}
}
