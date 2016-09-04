package com.ajreguyal.grouper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.ajreguyal.builder.CandleBuilder;
import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.model.Candle;
import com.ajreguyal.time.TimeFrame;

public class StockTimeDataGrouper {
	private Connection connection;
	private static final String TRANSACTION_TEXT = "transaction_";
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Candle getCandle(String stockName, long start, long end) throws SQLException {
		CandleBuilder builder = new CandleBuilder();
		if (connection == null) {
			throw new java.sql.SQLException("No connection");
		}
		
		String tableName = TRANSACTION_TEXT + stockName;
		
		String sql = new StringBuilder().append("SELECT distinct(select price from ").append(tableName).append(" \r\n").append( 
				"WHERE id >= ").append(start).append(" AND id < ").append(end).append(" order by id asc limit 1) AS priceOpen, \r\n").append(
				"(select price from ").append(tableName).append(" \r\n").append(
				"WHERE id >= ").append(start).append(" AND id < ").append(end).append(" order by id desc limit 1) AS priceClose, \r\n").append(
				"(select price from ").append(tableName).append(" \r\n").append(
				"WHERE id >= ").append(start).append(" AND id < ").append(end).append(" order by price asc limit 1) AS priceLow, \r\n").append( 
				"(select price from ").append(tableName).append(" \r\n").append(
				"WHERE id >= ").append(start).append(" AND id < ").append(end).append(" order by price desc limit 1) AS priceHigh, \r\n").append(
				"(select SUM(volume) from ").append(tableName).append(" \r\n").append(
				"WHERE id >= ").append(start).append(" AND id < ").append(end).append(") AS totalVolume FROM ").append(tableName).toString();
		
//		System.out.println(sql);
		
		try (PreparedStatement pSt = connection.prepareStatement(sql)){
			ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
            	builder.setLow(rs.getString("priceLow"));
            	builder.setHigh(rs.getString("priceHigh"));
            	builder.setOpen(rs.getString("priceOpen"));
            	builder.setClose(rs.getString("priceClose"));
            	builder.setVolume(rs.getString("totalVolume"));
            }
            pSt.close();
        } catch(Exception e) {
//            throw new java.sql.SQLException("Error getting table data count " + e.toString());
        }
		
 		return builder.build();
	}
	
	public static void main(String[] args) {
		DBBackend backend = new DBBackend();
		backend.connect();

		TimeFrame timeFrameManipulator = new TimeFrame();
		StockTimeDataGrouper grouper = new StockTimeDataGrouper();
		grouper.setConnection(backend.getConnection());
		long initialTime = timeFrameManipulator.createTimeId("2016/10/02", "9:30:00 AM");
		long currentTime = timeFrameManipulator.createTimeId("2016/10/02", "3:30:00 PM");//getNextTime(initialTime, 100);
		
		while (initialTime < currentTime) {
			int timeFrame = 5;
			try {
//				System.out.println("time: " 
//							+ timeFrameManipulator.convertTime(initialTime) 
//							+ " - " + timeFrameManipulator.convertTime(timeFrameManipulator.getNextTime(initialTime, timeFrame)  + 1000));
				Candle candle = (grouper.getCandle("med", initialTime, timeFrameManipulator.getNextTime(initialTime, timeFrame)  + 1000));
				
				if (candle == null) {
					System.out.println("no candle");
				} else {
					candle.setId(initialTime);
					candle.setTimeFrame(timeFrame);
					System.out.println(candle.toString());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initialTime = timeFrameManipulator.getNextTime(initialTime, timeFrame);
		}
	}
}
