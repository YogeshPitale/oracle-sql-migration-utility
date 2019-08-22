package connectionfactoryimpl;

import com.avaya.util.ResourceReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import com.avaya.dao.connectionfactory.ConnectionFactory;
import com.avaya.enums.ConnectionEnviroments;

public class ConnectionFactoryImpl implements ConnectionFactory {

	@Override
	public Connection getConnection(String type) throws PropertyException, SQLException {

		Connection con=null;
		
		Properties properties = ResourceReader.getProperties();
		
		if(properties==null)
			throw new PropertyException("Properties file not found");
		if(type==null||type.isEmpty())
			return null;
		else if (type.equalsIgnoreCase(ConnectionEnviroments.DEV.toString())) {
			con=DriverManager.getConnection(
					properties.getProperty("DEV_JDBC_DB_URL"), 
					properties.getProperty("DEV_JDBC_USER"), 
					properties.getProperty("DEV_JDBC_PASS"));
		}else if (type.equalsIgnoreCase(ConnectionEnviroments.QA.toString())) {
			con=DriverManager.getConnection(
					properties.getProperty("QA_JDBC_DB_URL"), 
					properties.getProperty("QA_JDBC_USER"), 
					properties.getProperty("QA_JDBC_PASS"));
		}else if (type.equalsIgnoreCase(ConnectionEnviroments.PRODUCTION.toString())) {
			con=DriverManager.getConnection(
					properties.getProperty("PRODUCTION_JDBC_DB_URL"), 
					properties.getProperty("PRODUCTION_JDBC_USER"), 
					properties.getProperty("PRODUCTION_JDBC_PASS"));
		}
		return con;
	}

	
}
