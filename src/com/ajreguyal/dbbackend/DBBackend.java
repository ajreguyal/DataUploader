/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajreguyal.dbbackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.apache.log4j.Logger;

//import mat2logger.MAT2LoggerInterface;


public class DBBackend {
    private Connection conn;

    private String  addr;
    private Integer port;
    private String  user;
    private String  pass;
    private String  name;
//    private final MAT2LoggerInterface localLogger;
    
    public enum CONNECTION {
        OK, ERROR
    }
    
    public DBBackend() {
        addr  = "127.0.0.1";
        port  = 3306;
        user  = "root";
        pass  = "root";
        name  = "stock";
//        localLogger = new MAT2LoggerInterface(this.getClass().getName(), "mat2parser");
    }

    public void setAddress(String address) {
        this.addr = address;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public void setDatabase(String name) {
        this.name = name;
    }
    
    public Boolean connect(String address, Integer port,String name, String user, String pass) {
        String  url;
        Boolean ready;
        
        url = "jdbc:mysql://" + address + ":" + port.toString() + "?useSSL=false";
        System.out.println(url);
        //url = "jdbc:mariadb://" + address + ":" + port.toString();
        try {
            //Class.forName("org.h2.Driver");
            //this.conn = DriverManager.getConnection("jdbc:h2:mem:mat","sa", "");
            //this.conn.setAutoCommit(false);

            //Class.forName("org.mariadb.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(true);
            
            ready = true;
        }
        catch(ClassNotFoundException | SQLException e) {
        	System.out.println(e.toString());
//            localLogger.logp(Level.SEVERE, "connect", e.toString());
            ready = false;
            conn = null;
        }
        
        if (ready) {
            // drop schema on MAT2Parser startup to make sure database is clean
            // before using
            //dropDatabase(name);
            createDatabase(name);
            //setUTF8();
        }
        
        return ready;
    }
    
    public Boolean connect() {
        return connect(this.addr, this.port, this.name, this.user, this.pass);
    }
    
    public Connection getConnection() {
        connect(this.addr, this.port, this.name, this.user, this.pass);
        return conn;
    }
    
    private void dropDatabase(String name) {
        String query = "DROP SCHEMA IF EXISTS " + name + ";";
                
        try {
            Statement st = conn.createStatement();
            st.execute(query);
        } catch(Exception e) {
        	System.out.println(e.toString());
//            localLogger.logp(Level.SEVERE, "dropDatabase", e.toString());
        }        
    }
    
    private void createDatabase(String name) {
        PreparedStatement pSt;
        String sql;
        
        sql = "CREATE SCHEMA IF NOT EXISTS " + name + ";";
        
        try {
            pSt = this.conn.prepareStatement(sql);
            pSt.execute();
            
            this.conn.setCatalog(name);
        }
        catch(Exception e) {
        	System.out.println(e.toString());
//            localLogger.logp(Level.SEVERE, "createDatabase", e.toString());
            //System.out.println(e.toString());
        }        
    }

    private void setUTF8() {
        PreparedStatement pSt;
        String sql;

        sql = "SET NAMES UTF8;";
        try {
            pSt = conn.prepareStatement(sql);
        
            pSt.execute();
        }
        catch(Exception e) {
        	System.out.println(e.toString());
//            localLogger.logp(Level.SEVERE, "setUTF8", e.toString());
            //System.out.println(e.toString());
        }
    }
}
