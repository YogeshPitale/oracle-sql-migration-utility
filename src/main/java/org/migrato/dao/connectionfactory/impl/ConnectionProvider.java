package org.migrato.dao.connectionfactory.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.migrato.util.ResourceReader;

import lombok.Getter;

@Getter
public class ConnectionProvider  {

	private Properties properties = ResourceReader.getProperties();
	private String dbURL; 
	private String dbUSer;
	private String dbPassword;
	private String type;
	
	
	public ConnectionProvider(String type) {
		super();
		if(type==null) throw new RuntimeException("Unable to instantiate ConnectionProvider object");
		this.dbURL = properties.getProperty(type.toUpperCase().intern()+"_JDBC_DB_URL");
		this.dbUSer = properties.getProperty(type.toUpperCase()+"_JDBC_USER");
		this.dbPassword = properties.getProperty(type.toUpperCase()+"_JDBC_PASS");
		this.type = type;
	}


	public Connection getConnection() throws PropertyException, SQLException, ClassNotFoundException {

		
		if(properties==null)
			throw new PropertyException("Properties file not found");
		if(type==null||type.isEmpty())
			return null;
		else {
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			return DriverManager.getConnection(this.getDbURL(),	this.getDbUSer(),this.getDbPassword());
		}
		/*
		 * else if (type.equalsIgnoreCase(ConnectionEnviroments.DEV.toString())) {
		 * con=DriverManager.getConnection( properties.getProperty("DEV_JDBC_DB_URL"),
		 * properties.getProperty("DEV_JDBC_USER"),
		 * properties.getProperty("DEV_JDBC_PASS")); }else if
		 * (type.equalsIgnoreCase(ConnectionEnviroments.QA.toString())) {
		 * con=DriverManager.getConnection( properties.getProperty("QA_JDBC_DB_URL"),
		 * properties.getProperty("QA_JDBC_USER"),
		 * properties.getProperty("QA_JDBC_PASS")); }else if
		 * (type.equalsIgnoreCase(ConnectionEnviroments.PRODUCTION.toString())) {
		 * con=DriverManager.getConnection(
		 * properties.getProperty("PRODUCTION_JDBC_DB_URL"),
		 * properties.getProperty("PRODUCTION_JDBC_USER"),
		 * properties.getProperty("PRODUCTION_JDBC_PASS")); }
		 */
		
		
	}

	
}
