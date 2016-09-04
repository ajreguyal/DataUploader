package com.ajreguyal.model;

public class Pivot {
	public static enum PivotType {
		GOING_DOWN,
		GOING_UP,
		UNKNOWN
	}
	
	private Candle candle;
	private PivotType type = PivotType.UNKNOWN;
	
	public Pivot(Candle candle, PivotType type) {
		setCandle(candle);
		setType(type);
	}
	
	public Candle getCandle() {
		return candle;
	}

	public void setCandle(Candle candle) {
		this.candle = candle;
	}

	public PivotType getType() {
		return type;
	}

	public void setType(PivotType type) {
		this.type = type;
	}
}
