package com.ajreguyal.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ajreguyal.builder.TransactionBuilder;
import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.model.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class TransactionXMLReader {
	public static final String VALID_FILE_EXTENSION = ".xml";
    private Element rootNode;
    private Connection connection = null;
    private String name;
    
    public TransactionXMLReader(File file) throws Exception {
    	if ((file == null)
    			|| !file.exists()
    			|| !file.isFile()){
    		throw new FileNotFoundException();
    	}
    	if (!file.getName().endsWith(VALID_FILE_EXTENSION)) {
    		throw new Exception("Invalid file");
    	}
    	name = file.getName().toLowerCase().replace(VALID_FILE_EXTENSION, "");
    	initRoot(file);
    }
    
    public void setConnection(Connection connection) {
    	this.connection = connection;
    }

	public void read() throws SQLException {
		if (connection == null) {
			throw new java.sql.SQLException("no connection");
		}
		
		String tablename = getTableName();
		createTable(tablename);
		
    	String sqlForInsertingDataToTable = "INSERT INTO " + tablename 
		+ " (id, date, time, buyer, seller, price, volume) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		TransactionBuilder builder = new TransactionBuilder();
		String date = (rootNode.getAttribute("date").getValue());
        builder.setDate(date);
        List list = rootNode.getChildren();
        for (Object transaction: list) {
            initializeDataValues(builder, transaction);
            try {
            	Transaction transactionObject = builder.create();
            	if (transactionObject != null) {
                    try {
                    	addDataToDB(transactionObject, sqlForInsertingDataToTable);
                    } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
                    	
                    } catch(Exception e) {
                        System.out.println("Problem inserting data to " + tablename + ": " + e.toString());
                    }
            	}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            builder.clear();
        }
	}

	public String getTableName() {
		return "`stock`.`transaction" + name + "`";
	}

	private void initializeDataValues(TransactionBuilder builder, Object transaction) {
		Element transactionElement = (Element) transaction;
		List transactionAttribute = transactionElement.getChildren();
		for (Object attribute: transactionAttribute) {
			String name = ((Element) attribute).getName();
		    String value = ((Element) attribute).getValue();
		    switch (name) {
		        case "time": builder.setTime(value);
		                    break;
		        case "price": builder.setPrice(value);
		        			break;
		        case "volume": builder.setVolume(value);
		        			break;
		        case "buyer": builder.setBuyer(value);
		        			break;
		        case "seller": builder.setSeller(value);
		        			break;
		    }
		}
	}

	private void createTable(String tablename) throws SQLException {
		try (PreparedStatement pSt = connection.prepareStatement(getSqlCommandForCreateTable(tablename))){
            pSt.execute();
            pSt.close();
        } catch(Exception e) {
            throw new java.sql.SQLException("Error creating table. " + e.toString());
        }
	}

	private void addDataToDB(Transaction transactionObject, String sql) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setDouble(1, (double) transactionObject.getTimeId());
		ps.setString(2, transactionObject.getDate());
		ps.setString(3, transactionObject.getTime());
		ps.setString(4, transactionObject.getBuyer());
		ps.setString(5, transactionObject.getSeller());
		ps.setFloat(6, transactionObject.getPrice());
		ps.setDouble(7, (double) transactionObject.getVolume());
		ps.execute();
		ps.close();
	}

	private String getSqlCommandForCreateTable(String tablename) {
		return "CREATE TABLE IF NOT EXISTS " + tablename + " (\r\n" + 
				"  `id` DOUBLE UNSIGNED ZEROFILL NOT NULL,\r\n" + 
				"  `date` VARCHAR(10) NULL,\r\n" + 
				"  `time` VARCHAR(15) NULL,\r\n" + 
				"  `buyer` VARCHAR(10) NULL,\r\n" + 
				"  `seller` VARCHAR(10) NULL,\r\n" + 
				"  `price` FLOAT NULL,\r\n" + 
				"  `volume` DOUBLE UNSIGNED NULL,\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" + 
				"  UNIQUE INDEX `id_UNIQUE` (`id` ASC));";
	}
	
	private void initRoot(File file) {
		SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(file);
            rootNode = document.getRootElement();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
	}
	

    private static final Logger log = LoggerFactory.getLogger(TransactionXMLReader.class);
    
	public static void main(String[] args) {
//		new TransactionXMLReader(new File("D:\\Sync\\Sync\\Stock\\Data\\Transaction\\2016-09-01\\2GO.xml"));
//		new TransactionXMLReader(new File("D:\\Sync\\Sync\\Stock\\Data\\Transaction\\2016-09-02\\2GO.xml"));
		
	}
}
