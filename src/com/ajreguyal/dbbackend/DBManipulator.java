package com.ajreguyal.dbbackend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManipulator {
	private Connection connection;
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public int getTableContentCount(String tableName) throws SQLException {
		int count = 0;
		try (PreparedStatement pSt = connection.prepareStatement("SELECT count(id) AS total FROM " + tableName)){
			ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("total"));
            }
            pSt.close();
        } catch(Exception e) {
            throw new java.sql.SQLException("Error getting table data count " + e.toString());
        }
		return count;
	}

	public void deleteTable(String tableName) throws SQLException {
		try (PreparedStatement pSt = connection.prepareStatement("DROP TABLE " + tableName)){
            pSt.execute();
            pSt.close();
        } catch(Exception e) {
            throw new java.sql.SQLException("Error creating table. " + e.toString());
        }
	}
}
