package com.ajreguyal.main.java;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.reader.TransactionXMLReader;

public class TransactionXMLReaderTest {
	private static DBBackend backend; 
	private static File validXMLFile;
	private static File invalidXMLFile;
	
	public TransactionXMLReaderTest() {
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
					"</TransactionDailyReview>";
			

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
		validXMLFile.delete();
	}

	@Test (expected = FileNotFoundException.class)
	public void testNoFile() throws Exception {
		new TransactionXMLReader(null);
		new TransactionXMLReader(new File("C:\\Trial"));
	}

	@Test (expected = Exception.class)
	public void testInvalidFile() throws Exception {
		new TransactionXMLReader(invalidXMLFile);
	}

	@Test (expected = SQLException.class)
	public void testNoConnection() throws Exception {
		com.ajreguyal.reader.TransactionXMLReader readerFile = new TransactionXMLReader(validXMLFile);
		readerFile.read();
	}

	@Test
	public void testRead() throws Exception {
		com.ajreguyal.reader.TransactionXMLReader readerFile = new TransactionXMLReader(validXMLFile);

		backend = new DBBackend();
		backend.connect();
		
		deleteTable(backend.getConnection(), readerFile.getTableName());
		
		readerFile.setConnection(backend.getConnection());
		readerFile.read();
	}

	private void deleteTable(Connection connection, String tableName) throws SQLException {
		System.out.println("DROP " + tableName);
		try (PreparedStatement pSt = connection.prepareStatement("DROP TABLE " + tableName)){
            pSt.execute();
        } catch(Exception e) {
            throw new java.sql.SQLException("Error creating table. " + e.toString());
        }
	}
}
