package org.migrato.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.migrato.dao.connectionfactory.impl.ConnectionProvider;

public class DBOperationsDAO {
 
    public final static Logger logger = Logger.getLogger(DBOperationsDAO.class);
    
    public String performDDLOperations(Connection con,String query) {
    	if(query!=null && !query.trim().isEmpty()) {
	    	try (Statement stmtOBj = con.createStatement()){
	    		System.out.println("---------------------------------------------");
	            System.out.println("Executing DDL Query: '"+ query+"'");           
	            stmtOBj.executeUpdate(query);
	            return ("Success : Query execution was successful.");
	        } catch(Exception sqlException) {
	        	return ("FAIL : Unable to execute query. Error -> "+sqlException.getMessage());
	        } 
    	}else
        	return "Nothing to execute in DDL!";
    }
    
    public String performDMLOperations(Connection con,String query) {
    	if(query!=null && !query.trim().isEmpty()) {
	    	try (CallableStatement stmtOBj = con.prepareCall(query)){
	    		System.out.println("---------------------------------------------");
	            System.out.println("Executing DML Query: '"+ query+"'");           
	            stmtOBj.execute(query);
	            return ("Success : Query execution was successful.");
	        } catch(Exception sqlException) {
	        	return ("FAIL : Unable to execute query. Error -> "+sqlException.getMessage());
	        } 
    	}else
        	return "Nothing to execute in DML!";
    }
    
    public String getObjectCode(String objectName, String ObjectType, String owner, Connection con) throws SQLException {
    	StringBuilder result = new StringBuilder();
    	String sql = "select text from all_source where owner=? and name = ? and type = ? order by line"; 
    	try(PreparedStatement pstmt = con.prepareStatement(sql)){
    		pstmt.setString(1, owner);
    		pstmt.setString(2, objectName);
    		pstmt.setString(3, ObjectType);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next())
    			result.append(rs.getString("TEXT"));
    	}
    	return result.toString();
    }
    
    public String getObjectDDL(String objectName, String ObjectType, ConnectionProvider provider, Connection con) throws SQLException {
    	StringBuilder result = new StringBuilder();
    	String sql = "SELECT fetch_object_creation_script(?,?,? ) as TEXT FROM dual"; 
    	try(PreparedStatement pstmt = con.prepareStatement(sql)){
    		pstmt.setString(1, ObjectType);
    		pstmt.setString(2, objectName);
    		pstmt.setString(3, provider.getDbUSer());
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next())
    			result.append(rs.getString("TEXT"));
    	}
    	return result.toString();
    }
    
    public String getObjectsListByType(String fromDate, String objectType, String schemaName, Connection con) throws SQLException {
    	StringBuilder result = new StringBuilder();
    	String sql = "select OBJECT_NAME from SYS.all_objects "
    			+ "where LAST_DDL_TIME > to_date(?,'dd-mm-YYYY') and object_type=? and owner=?"; 
    	try(PreparedStatement pstmt = con.prepareStatement(sql)){
    		pstmt.setString(1, fromDate);
    		pstmt.setString(2, objectType);
    		pstmt.setString(3, schemaName);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next())
    			result.append(rs.getString("OBJECT_NAME")+";");
    	}
    	return result.toString();
    }
    
}
