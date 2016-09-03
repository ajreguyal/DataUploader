package com.ajreguyal.main.java;

import org.springframework.context.annotation.*;

@Configuration
public class DataUploaderConfig {
   @Bean 
   public DataUploader dataUploader(){
      return new DataUploader();
   }
}
