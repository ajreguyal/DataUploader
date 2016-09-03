package com.ajreguyal.main.java;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	public static void main(String[] args) {
		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(DataUploaderConfig.class);

		DataUploader uploader = ctx.getBean(DataUploader.class);
//		uploader.uploadFile(args[0]);
//		helloWorld.setMessage("Hello World!");
//		helloWorld.getMessage();
	}
}
