package com.ajreguyal.main.java;

import java.io.File;

import com.ajreguyal.dbbackend.DBBackend;
import com.ajreguyal.reader.TransactionXMLReader;

public class DataUploader {
	public void uploadFile(String file) {
		com.ajreguyal.reader.TransactionXMLReader reader;
		try {
			reader = new TransactionXMLReader(new File(file));
			DBBackend backend = new DBBackend();
			backend.connect();
			reader.setConnection(backend.getConnection());
			reader.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DataUploader uploader = new DataUploader();
		if (args.length > 0) {
			System.out.println(args[0]);
			uploader.uploadFile(args[0]);
		}
	}
} 
