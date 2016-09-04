package com.ajreguyal.finder;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.grouper.StockTimeDataGrouper;
import com.ajreguyal.model.Candle;
import com.ajreguyal.model.Pivot;
import com.ajreguyal.model.Pivot.PivotType;
import com.ajreguyal.time.TimeFrame;

public class PivotFinder {
	private ArrayList<Candle> candleList = null;
	private ArrayList<Pivot> pivotList = null;
	private int currentPivot = 1;
	
	public PivotFinder() {
		
	}
	
	public ArrayList<Candle> getCandleList() {
		if (candleList == null) {
			candleList = new ArrayList<>();
		}
		return candleList;
	}

	public void setCandleList(ArrayList<Candle> candleList) {
		this.candleList = candleList;
	}

	public ArrayList<Pivot> getPivotList() {
		if (pivotList == null) {
			pivotList = new ArrayList<>();
		}
		return pivotList;
	}
	
	public void addCandle(Candle candle) {
		getCandleList().add(candle);
		updatePivotList();
	}

	private void updatePivotList() {
		if (getCandleList().size() > currentPivot) {
			Candle leftCandle = getCandle(currentPivot - 1);
			Candle pivotCandle = getCandle(currentPivot);
			Candle rightCandle = getCandle(currentPivot + 1);
			if (isAllCandleValid(leftCandle, pivotCandle, rightCandle)) {
				float leftHigh = leftCandle.getHigh();
				float pivotHigh = pivotCandle.getHigh();
				float rightHigh = rightCandle.getHigh();
				
//				System.out.println("\thigh comparison: " + leftHigh + " < " + pivotHigh + " > " + rightHigh);
				if ((pivotHigh > leftHigh)
						&& (pivotHigh > rightHigh)) {
					addPivot(pivotCandle, Pivot.PivotType.GOING_DOWN);
				} else {
				
					float leftLow = leftCandle.getLow();
					float pivotLow = pivotCandle.getLow();
					float rightLow = rightCandle.getLow();
					
//					System.out.println("\tlow comparison: " + leftLow + " < " + pivotLow + " > " + rightLow);
					
					if ((pivotLow < rightLow)
							&& (pivotLow < leftLow)) {
						addPivot(pivotCandle, Pivot.PivotType.GOING_UP);
					}
				}
				currentPivot++;
			} else {
				System.out.println("pivots problems");
				if (leftCandle == null) {
					System.out.println("left problem");
				}
				if (pivotCandle == null) {
					System.out.println("pivot problem");
				}
				if (rightCandle == null) {
					System.out.println("right problem");
				}
			}
		}
	}

	private void addPivot(Candle candle, PivotType type) {
		getPivotList().add(new Pivot(candle, type));
		System.out.println(candle.toString() + "\tindex: " + currentPivot + " is " + type.toString());
	}

	private boolean isAllCandleValid(Candle... candles) {
		boolean isAllValid = true;
		for (Candle candle: candles) {
			if (candle == null) {
				isAllValid = false;
				break;
			}
		}
		return isAllValid;
	}

	private Candle getCandle(int index) {
		Candle candle = null;
		if ((index > -1)
				&& (index < getCandleList().size())) {
			candle = getCandleList().get(index);
		}
		return candle;
	}
	
	public static void main(String[] args) {
		DBBackend backend = new DBBackend();
		backend.connect();

		TimeFrame timeFrameManipulator = new TimeFrame();
		StockTimeDataGrouper grouper = new StockTimeDataGrouper();
		grouper.setConnection(backend.getConnection());
		long initialTime = timeFrameManipulator.createTimeId("2016/10/02", "9:30:00 AM");
		long currentTime = timeFrameManipulator.createTimeId("2016/10/02", "3:30:00 PM");//getNextTime(initialTime, 100);
		
		PivotFinder pivotFinder = new PivotFinder();
		while (initialTime < currentTime) {
			int timeFrame = 5;
			try {
				System.out.println("time: " 
							+ timeFrameManipulator.convertTime(initialTime) 
							+ " - " + timeFrameManipulator.convertTime(timeFrameManipulator.getNextTime(initialTime, timeFrame)  + 1000));
				Candle candle = (grouper.getCandle("med", initialTime, timeFrameManipulator.getNextTime(initialTime, timeFrame)  + 1000));
				if (candle == null) {
					System.out.println("no candle");
				} else {
//					System.out.println(candle.toString());
					pivotFinder.addCandle(candle);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initialTime = timeFrameManipulator.getNextTime(initialTime, timeFrame);
		}
	}
}
