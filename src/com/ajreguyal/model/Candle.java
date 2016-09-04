package com.ajreguyal.model;

import com.ajreguyal.time.TimeFrame;

public class Candle {
//	private static TimeFrame timeFrame = new TimeFrame();
//	private static long timeData = timeFrame.createTimeId("2016/10/02", "1:00:00 AM");
	private float low;
	private float high;
	private float open;
	private float close;
	private float volume;
	
	private int timeFrame;
	private long id;
	
	public Candle() {
		
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
	}
	
	public int getTimeFrame() {
		return this.timeFrame;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public float getVolume() {
		return this.volume;
	}
	
	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}
	
	@Override
	public String toString() {
//		timeData += 86400000;
		
//		return timeFrame.convertDate(timeData) + "," + getOpen() + "," + getClose() + "," + getHigh() + "," + getLow() + "," + getVolume();
		return "Open: " + getOpen() + ". Close:" + getClose()
			+ ". High: " + getHigh() + ". Low: " + getLow() + ". Volume: " + getVolume();
	}
}
