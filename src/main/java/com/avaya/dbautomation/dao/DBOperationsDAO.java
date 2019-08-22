package com.avaya.dbautomation.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DBOperationsDAO {
 
    public final static Logger logger = Logger.getLogger(DBOperationsDAO.class);
    
    public String performDDLOperations(Connection con,String query) {
    	try (Statement stmtOBj = con.createStatement()){
    		System.out.println("---------------------------------------------");
            System.out.println("Executing DDL Query: '"+ query+"'");           
            stmtOBj.executeUpdate(query);
            return ("Success : Query execution was successful.");
        } catch(Exception sqlException) {
        	return ("FAIL : Unable to execute query. Error -> "+sqlException.getMessage());
        } 
    }
    
    public String performDMLOperations(Connection con,String query) {
    	try (CallableStatement stmtOBj = con.prepareCall(query)){
    		System.out.println("---------------------------------------------");
            System.out.println("Executing DML Query: '"+ query+"'");           
            stmtOBj.execute(query);
            return ("Success : Query execution was successful.");
        } catch(Exception sqlException) {
        	return ("FAIL : Unable to execute query. Error -> "+sqlException.getMessage());
        } 
    }
    
    public String getObjectCode(String objectName, String ObjectType, Connection con) throws SQLException {
    	StringBuilder result = new StringBuilder();
    	String sql = "select text from all_source where name = ? and type = ? order by line"; 
    	try(PreparedStatement pstmt = con.prepareStatement(sql)){
    		pstmt.setString(1, objectName);
    		pstmt.setString(2, ObjectType);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next())
    			result.append(rs.getString("TEXT"));
    	}
    	return result.toString();
    }
}
