/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ajreguyal.dbbackend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
//import mat2logger.MAT2LoggerInterface;

public class DBInit {
    private Connection conn;
//    private MAT2LoggerInterface localLogger;
    
    public DBInit() {
//        localLogger = new MAT2LoggerInterface(this.getClass().getName(), "mat2parser");
    }
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void createTables() {
        createCommandTable();
        createResponseTable();
        createScriptTable();
        //createFile_alan_Table();
        //createFileTable();
        createResultsTable();
        createFileTable();
        createKeywordTable();
        createConfigTable();
        createTimeStampTable();
        createFunctionTable();
        createSeriesTable();
        createFuncLibTable();
        createCleanSpaceScheduleTable();
        createTimeCalibrationTable();
        // dhaggs_btslog
        //updateDB();
    }
    
    private void createCommandTable () {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS command ("
            + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id  VARCHAR(15) NOT NULL DEFAULT '', "
            + "cmd      VARCHAR(4096) NOT NULL DEFAULT '', "
            + "cmd_type VARCHAR(16) NOT NULL DEFAULT '', "
            + "started  INTEGER NOT NULL DEFAULT NULL DEFAULT 0, "
            + "file_cnt INTEGER NOT NULL DEFAULT NULL DEFAULT 0, "
            + "PRIMARY  KEY (id)"
            + ")";

        executeSQL(sql);
    }
    
    private void createResponseTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS response ("
            + "id           INTEGER NOT NULL AUTO_INCREMENT, "
            + "cmd_id       INTEGER NOT NULL DEFAULT 0, "
            + "user_id      INTEGER NOT NULL DEFAULT 0, "
            + "cmd          VARCHAR(4096) NOT NULL DEFAULT '', "
            + "total_line   INTEGER NOT NULL DEFAULT 0, "
            + "current_line INTEGER NOT NULL DEFAULT 0, "
            + "response     VARCHAR(256) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql);
    }

    private void createScriptTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS script ("
            + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            //+ "datetime DATETIME NOT NULL DEFAULT '1972-09-16 09:16:12', "
            + "datetime VARCHAR(19) NOT NULL DEFAULT '', "
            + "user_id  VARCHAR(15) NOT NULL DEFAULT '', "
            + "line     INTEGER NOT NULL DEFAULT 0, "
            + "cmd      VARCHAR(4096) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql);
    }

    private void createResultsTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS results ("
            + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id  VARCHAR(15) NOT NULL DEFAULT '', "
            + "script   VARCHAR(64) NOT NULL DEFAULT '', "
            + "cmd      VARCHAR(4096) NOT NULL DEFAULT '', "
            + "line     INTEGER NOT NULL DEFAULT 0, "
            + "results  VARCHAR(8196) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id), "
            + "INDEX line_idx (line) "
            + ")";
        
        executeSQL(sql);
    }
    
    private void createFileTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS file ("
            + "id            INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id       INTEGER NOT NULL DEFAULT 0, "
            + "folder        VARCHAR(512) NOT NULL DEFAULT '', "
            + "filename      VARCHAR(64) NOT NULL DEFAULT '', "
            + "filetype      VARCHAR(64) NOT NULL DEFAULT '', "
            + "subtype       VARCHAR(64) NOT NULL DEFAULT '', "
            + "tablename     VARCHAR(64) NOT NULL DEFAULT '', "
            + "total_line    INTEGER NOT NULL DEFAULT 0, "
            + "hash          VARCHAR(64) NOT NULL DEFAULT '', "
                
            + "file_user     VARCHAR(16) NOT NULL DEFAULT '', "
            + "file_date     VARCHAR(16) NOT NULL DEFAULT '', "
            + "file_time     VARCHAR(16) NOT NULL DEFAULT '', "
            + "file_segment  VARCHAR(16) NOT NULL DEFAULT '', "
            + "calibrated    INTEGER NOT NULL DEFAULT 0, "
            + "create_date   TIMESTAMP   NOT NULL DEFAULT NOW(), "
                
            + "PRIMARY KEY (id)"
            + ")";
        
        
            //System.out.println(this.filePath);
            //System.out.println(this.fileName);
            //System.out.println(this.dctType + " <-> " + this.fileUser);
            //System.out.println(this.fileUser);
            //System.out.println(this.fileDate);
            //System.out.println(this.fileTime);
            //System.out.println(this.fSegment);
        executeSQL(sql);
    }

    /*private void createFile_alan_Table() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS files ("
            + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            + "cmd      VARCHAR(4096) NOT NULL DEFAULT '', "
            + "results  VARCHAR(8196) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql);
    }
    }*/
        
    private void createKeywordTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS keywords ("
           // + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            + "keywords VARCHAR(128) NOT NULL DEFAULT '', "                
            + "filetype VARCHAR(128) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (keywords, filetype)"
            + ")";
        
        executeSQL(sql); 
    }
    
    private void createConfigTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS config ("
            + "id        INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id   INTEGER NOT NULL DEFAULT 0, "
            + "var_type  VARCHAR(64) NOT NULL DEFAULT '', "                
            + "var_name  VARCHAR(128) NOT NULL DEFAULT '', "
            + "var_value VARCHAR(128) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql); 
    }

    private void createFunctionTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS function ("
            + "id        INTEGER NOT NULL AUTO_INCREMENT, "
            + "line      INTEGER NOT NULL DEFAULT 0, "
            + "name      VARCHAR(128) NOT NULL DEFAULT '', "
            + "statement VARCHAR(4096) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql); 
    }
    
    
    private void createSeriesTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS series ("
            + "id        INTEGER NOT NULL AUTO_INCREMENT, "
            + "line      INTEGER NOT NULL DEFAULT 0, "
            + "name      VARCHAR(128) NOT NULL DEFAULT '', "
            + "statement VARCHAR(4096) NOT NULL DEFAULT '', "
            + "date_inserted DATE, "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql); 
    }
    
    private void createFuncLibTable() {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS funclib ("
            + "id        INTEGER NOT NULL AUTO_INCREMENT, "
            + "line      INTEGER NOT NULL DEFAULT 0, "
            + "name      VARCHAR(128) NOT NULL DEFAULT '', "
            + "statement VARCHAR(4096) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql); 
    }
    
    private void createTimeCalibrationTable() {
        String sql;
        
        sql = "CREATE TABLE IF NOT EXISTS time_calibration ("
            + "id            INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id       INTEGER NOT NULL DEFAULT 0, "
            + "folder        VARCHAR(512) NOT NULL DEFAULT '', "
            + "type          VARCHAR(64) NOT NULL DEFAULT '',"
            + "adjustment    INTEGER NOT NULL DEFAULT '0',  "
            + "errorcode     INTEGER, "
            + "PRIMARY KEY (id)"
            + ")";
        
        executeSQL(sql); 
    }
    
    private void updateDB() {
        String sql;
        
        
        String dbName;
        try {
            dbName = this.conn.getSchema();
        }
        catch(Exception e) {
            dbName = null;
            System.out.println("Exception" +e.getMessage());
        }
        if (dbName  != null)
            sql = "ALTER TABLE "+dbName+"btslog MODIFY btslog.message VARCHAR(2048)";
        else
            sql = "ALTER TABLE btslog MODIFY btslog.message VARCHAR(2048)";
        
        executeSQL(sql);
    }

    private void createTimeStampTable () {
    
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS timestamp ("
            + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id  INTEGER NOT NULL DEFAULT 0, "
            + "folder   VARCHAR(255) NOT NULL DEFAULT '', "
            + "filename VARCHAR(255) NOT NULL DEFAULT '', "
            + "time_id  VARCHAR(255) NOT NULL DEFAULT '', "
            + "time     TIME(6) NOT NULL, "
            + "date     DATE NOT NULL, "
            + "col1     VARCHAR(255) NOT NULL DEFAULT '', "
            + "PRIMARY KEY (id), "
            + "UNIQUE KEY uc_timeID (folder, time_id)"
            + ")";
        
        executeSQL(sql);
    }

    private void createCleanSpaceScheduleTable () {
        String sql;
        
        sql = "CREATE TABLE  IF NOT EXISTS cleanspaceschedule ("
            + "id       INTEGER NOT NULL AUTO_INCREMENT, "
            + "user_id  VARCHAR(15) NOT NULL DEFAULT '', "
            + "schedule VARCHAR(32) NOT NULL DEFAULT '', "
            + "PRIMARY  KEY (id)"
            + ")";

        executeSQL(sql);
    }
    
    private void executeSQL(String sql) {
        //PreparedStatement pSt = null;

        // dhaggs
        try (PreparedStatement pSt = this.conn.prepareStatement(sql)){
            //pSt = this.conn.prepareStatement(sql);
            pSt.execute();
            //this.conn.commit();
        }
        catch(Exception e) {
//            localLogger.logp(Level.SEVERE, "executeSQL", e.toString());
            //System.out.println(e.toString());
        }
    }
}
