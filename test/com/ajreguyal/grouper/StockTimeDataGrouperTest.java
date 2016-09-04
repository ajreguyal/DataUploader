package com.ajreguyal.grouper;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ajreguyal.builder.CandleBuilder;
import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.dbbackend.DBManipulator;
import com.ajreguyal.model.Candle;
import com.ajreguyal.reader.TransactionXMLReader;
import com.ajreguyal.time.TimeFrame;

public class StockTimeDataGrouperTest {
	private static DBBackend backend; 
	private static File validXMLFile;
	private static File invalidXMLFile;
	
	public StockTimeDataGrouperTest() {
		
	}

	@BeforeClass
	public static void initFiles() {
		try {
			validXMLFile = new File("validDataTest.xml");
			invalidXMLFile = new File("invalidDataTest.txt");
			validXMLFile.createNewFile();
			invalidXMLFile.createNewFile();
			
			String validData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
					"<TransactionDailyReview date=\"2016/09/01\" code=\"2GO\">\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>9:30:00 AM</time>\r\n" + 
					"    <price>7.17</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>9:32:29 AM</time>\r\n" + 
					"    <price>7.17</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>300</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>9:35:55 AM</time>\r\n" + 
					"    <price>7.16</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>1,000</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>9:42:15 AM</time>\r\n" + 
					"    <price>7.16</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>900</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>9:45:32 AM</time>\r\n" + 
					"    <price>7.17</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>1,000</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>9:46:57 AM</time>\r\n" + 
					"    <price>7.17</price>\r\n" + 
					"    <updown>tranItemBUp</updown>\r\n" + 
					"    <volume>2,400</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>10:05:24 AM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>tranItemBUp</updown>\r\n" + 
					"    <volume>2,500</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>10:28:34 AM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>tranItemBUp</updown>\r\n" + 
					"    <volume>3,000</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>10:51:05 AM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>1:47:22 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>500</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>1:53:35 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:36:00 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>1,000</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:44:05 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>300</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:46:22 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>7,700</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>FMSB</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:52:55 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>2,000</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:56:45 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>10,000</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:56:52 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:57:06 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:57:20 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:59:12 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>5,100</volume>\r\n" + 
					"    <buyer>BDO</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>2:59:36 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>4,000</volume>\r\n" + 
					"    <buyer>BPISEC</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:02:57 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnSDown</updown>\r\n" + 
					"    <volume>200</volume>\r\n" + 
					"    <buyer>BPISEC</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:12:24 PM</time>\r\n" + 
					"    <price>7.17</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>500</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:20:00 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>2,000</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:20:08 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>5,000</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:23:11 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown />\r\n" + 
					"    <volume>100</volume>\r\n" + 
					"    <buyer>COL</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:28:04 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>1,700</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>MANDARIN</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:29:41 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>600</volume>\r\n" + 
					"    <buyer>IGC_SEC</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"  <transaction>\r\n" + 
					"    <time>3:29:51 PM</time>\r\n" + 
					"    <price>7.20</price>\r\n" + 
					"    <updown>trnBUp</updown>\r\n" + 
					"    <volume>300</volume>\r\n" + 
					"    <buyer>ABACUS</buyer>\r\n" + 
					"    <seller>COL</seller>\r\n" + 
					"  </transaction>\r\n" + 
					"</TransactionDailyReview>\r\n" + 
					"\r\n" + 
					"";
			

			FileWriter fw = new FileWriter(validXMLFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(validData);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void clean() {
		invalidXMLFile.delete();
		
		try {
			com.ajreguyal.reader.TransactionXMLReader readerFile = new TransactionXMLReader(validXMLFile);

			backend = new DBBackend();
			backend.connect();
			
			DBManipulator manipulator = new DBManipulator();
			manipulator.setConnection(backend.getConnection());
			manipulator.deleteTable(readerFile.getTableName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCandle() throws Exception {
		DBBackend backend = new DBBackend();
		backend.connect();
		
		StockTimeDataGrouper grouper = new StockTimeDataGrouper();
		grouper.setConnection(backend.getConnection());

		com.ajreguyal.reader.TransactionXMLReader readerFile = new TransactionXMLReader(validXMLFile);
		readerFile.setConnection(backend.getConnection());
		readerFile.read();
		
		TimeFrame timeFrameManipulator = new TimeFrame();

		int minutes = 5;
		long timeStart = timeFrameManipulator.createTimeId("2016/09/01", "9:30:00 AM");
		long timeAfterNMinutes = timeFrameManipulator.getNextTime(timeStart, minutes) + 1000;
		
		CandleBuilder candleBuilder = new CandleBuilder();
		
		//timeframe 5 minutes
		candleBuilder.setOpen("7.17");
		candleBuilder.setClose("7.17");
		candleBuilder.setHigh("7.17");
		candleBuilder.setLow("7.17");
		candleBuilder.setVolume("400.0");
		
		Candle candle = grouper.getCandle("validDataTest", timeStart, timeAfterNMinutes + 1000);
		assertTrue(candle.equals(candleBuilder.build()));
		
		//timeframe 3 minutes
		minutes = 5;
		timeStart = timeFrameManipulator.createTimeId("2016/09/01", "09:33:00 AM");
		timeAfterNMinutes = timeFrameManipulator.getNextTime(timeStart, minutes) + 1000;
		
		candleBuilder.setOpen("7.16");
		candleBuilder.setClose("7.16");
		candleBuilder.setHigh("7.16");
		candleBuilder.setLow("7.16");
		candleBuilder.setVolume("1000.0");
		
		candle = grouper.getCandle("validDataTest", timeStart, timeAfterNMinutes + 1000);
		assertTrue(candle.equals(candleBuilder.build()));
		
		//timeframe 60 minutes
		minutes = 30;
		timeStart = timeFrameManipulator.createTimeId("2016/09/01", "09:30:00 AM");
		timeAfterNMinutes = timeFrameManipulator.getNextTime(timeStart, minutes) + 1000;
		
		candleBuilder.setOpen("7.17");
		candleBuilder.setClose("7.2");
		candleBuilder.setHigh("7.2");
		candleBuilder.setLow("7.16");
		candleBuilder.setVolume("11200.0");
		
		candle = grouper.getCandle("validDataTest", timeStart, timeAfterNMinutes + 1000);
		assertTrue(candle.equals(candleBuilder.build()));
		
		//timeframe 1 day (9:30 AM - 3:30PM)
		minutes = 360;
		timeStart = timeFrameManipulator.createTimeId("2016/09/01", "09:30:00 AM");
		timeAfterNMinutes = timeFrameManipulator.getNextTime(timeStart, minutes) + 1000;
		
		candleBuilder.setOpen("7.17");
		candleBuilder.setClose("7.2");
		candleBuilder.setHigh("7.2");
		candleBuilder.setLow("7.16");
		candleBuilder.setVolume("52700.0");
		
		candle = grouper.getCandle("validDataTest", timeStart, timeAfterNMinutes + 1000);
		assertTrue(candle.equals(candleBuilder.build()));
				
		//no candle
		minutes = 5;
		timeStart = timeFrameManipulator.createTimeId("2016/09/01", "11:55:00");
		timeAfterNMinutes = timeFrameManipulator.getNextTime(timeStart, minutes) + 1000;
		candle = grouper.getCandle("validDataTest", timeStart, timeAfterNMinutes + 1000);
		assertTrue(candle.equals(null));
	}
}
