package org.migrato.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection getConnection(String JDBC_DB_URL, String JDBC_USER, String JDBC_PASS) {
		Connection connObj=null; 
		try {
			connObj =  DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connObj;
	}
	
}
