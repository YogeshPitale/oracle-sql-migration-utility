package com.avaya.service.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import com.avaya.dao.connectionfactory.ConnectionFactory;
import com.avaya.dbautomation.dao.DBOperationsDAO;
import com.avaya.dbautomation.xmlbeans.DBObject;
import com.avaya.dbautomation.xmlbeans.ObjectXMLBean;
import com.avaya.dbautomation.xmlbeans.ScriptXMLBean;
import com.avaya.enums.ConnectionEnviroments;
import com.avaya.enums.DBObjectTypes;
import com.avaya.enums.DirectoryNames;
import com.avaya.service.AutomationService;
import com.avaya.util.FileUtils;

import connectionfactoryimpl.ConnectionFactoryImpl;

public class AutmationServiceImpl implements AutomationService{

	ConnectionFactory factory = new ConnectionFactoryImpl();
	
	public void excecuteScript() throws JAXBException {
	
		if(!FileUtils.getDirectoryMap().isEmpty()) {
			int counter=0;
			System.out.println("************Starting the deployment************");
			for(DirectoryNames installDirectory:DirectoryNames.values()) {
				if(!installDirectory.toString().equalsIgnoreCase("migrate")) {
					if(FileUtils.getDirectorySizeMap().get(installDirectory.toString())>0) {
								System.out.println("\n\n**Running " +installDirectory+" scripts**" );
								System.out.println("///////////////////////////////////////////////////////");
								for(File file : FileUtils.getDirectoryMap().get(installDirectory.toString())) {
									System.out.println("Processing file " + (++ counter) + " with length " + file.length());
									ScriptXMLBean scriptXMLBean = FileUtils.readFile(ScriptXMLBean.class, file);
									//scriptXMLBean = (ScriptXMLBean) unmarshaller.unmarshal(file);
									executeScripts(scriptXMLBean);	
								}
								System.out.println("Finished " +installDirectory+" scripts" );
								System.out.println("///////////////////////////////////////////////////////");
					}else
						System.out.println("No " + installDirectory+" scripts found" );
				}
			}
			System.out.println("************Deployment Finished************" );
		}else {
			System.out.println("No directories found at given location" );
		}
		
	}
	
	private static void executeScripts(ScriptXMLBean scriptXMLBean){
		if(scriptXMLBean!=null && scriptXMLBean.getSqlList().size()>0) {
			ConnectionFactory factory = new ConnectionFactoryImpl();
			try (Connection con = factory.getConnection(ConnectionEnviroments.DEV.toString())){
				DBOperationsDAO dao = new DBOperationsDAO();
				
				if(scriptXMLBean.getSqlList().get(0).getDdl().indexOf(";")==-1)
					System.out.println(dao.performDDLOperations(con, scriptXMLBean.getSqlList().get(0).getDdl().replace(";", "").trim()));
				else {
					String []ddls=scriptXMLBean.getSqlList().get(0).getDdl().split(";");
					for (String ddl : ddls) {
						if(ddl.trim()!=null && !ddl.trim().isEmpty())
							System.out.println(dao.performDDLOperations(con, ddl.replace(";", "")));
					}
					System.out.println("---------------------------------------------");
				}
				
				System.out.println(dao.performDMLOperations(con, scriptXMLBean.getSqlList().get(0).getDml().trim()));
			} catch (PropertyException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public void performMigration(String sourceEnoviroment, String destinationEnoviroment) throws JAXBException {
		if(!FileUtils.getDirectoryMap().isEmpty()) {
			int counter=0;
			System.out.println("************Starting the migration************");
			for(DirectoryNames installDirectory:DirectoryNames.values()) {
				if(installDirectory.toString().equalsIgnoreCase("migrate")) {
					if(FileUtils.getDirectorySizeMap().get(installDirectory.toString())>0) {
						System.out.println("\n\n**Running " +installDirectory+" scripts**" );
						System.out.println("///////////////////////////////////////////////////////");
						for(File file : FileUtils.getDirectoryMap().get(installDirectory.toString())) {
							System.out.println("Processing file " + (++ counter) + " with length " + file.length());
							ObjectXMLBean objectXMLBean = FileUtils.readFile(ObjectXMLBean.class, file);
							migrate(objectXMLBean,sourceEnoviroment,destinationEnoviroment);	
						}
						System.out.println("Finished " +installDirectory+" scripts" );
						System.out.println("///////////////////////////////////////////////////////");
					}else
						System.out.println("No " + installDirectory+" scripts found" );
				}
			}
			System.out.println("************Deployment Finished************" );
		}else {
			System.out.println("No directories found at given location" );
		}
		
	}

	private void migrate(ObjectXMLBean objectXMLBean, String sourceEnoviroment, String destinationEnoviroment) {
		Map<String,List<DBObject>> objectMap = new HashMap<>();
		if(objectXMLBean!=null && objectXMLBean.getFunctions()!=null && !objectXMLBean.getFunctions().isEmpty()) {
			for(DBObjectTypes objectType: DBObjectTypes.values()) {
				List<DBObject> objectList= extractFuntions(sourceEnoviroment,objectXMLBean,objectType.toString());
				if(objectList.size()>0)
					objectMap.put(objectType.toString(),objectList);
			}
			exportFunctions(objectMap,destinationEnoviroment);
		}	
	}
	
	private List<DBObject> extractFuntions(String source, ObjectXMLBean objectXMLBean, String objectType){
		List<DBObject> dbObectList = new ArrayList<>(); 
		try (Connection con = factory.getConnection(source)){
			DBOperationsDAO dao = new DBOperationsDAO();
			String objectName = objectXMLBean.getObjectByType(objectType).trim();
			if(objectName!=null && !objectName.trim().isEmpty()) {
				if(objectName.indexOf(";")!=-1) {
					String []dbObjects =objectName.split(";");
					for(String currentDbObject : dbObjects) {
						if(currentDbObject!=null && !currentDbObject.isEmpty()) {
						String objectCode = dao.getObjectCode(currentDbObject.trim(), objectType, con);
						DBObject dbObject = new DBObject(currentDbObject.trim(),objectCode,objectType);
						dbObectList.add(dbObject);
						}
					}
				}else {
					String objectCode = dao.getObjectCode(objectXMLBean.getObjectByType(objectType).trim(), objectType, con);
					DBObject dbObject = new DBObject(objectName,objectCode,objectType);
					dbObectList.add(dbObject);
				}
			}
			
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbObectList;
	}
	
	private void exportFunctions(Map<String,List<DBObject>> objectMap, String destination) {
		try (Connection con = factory.getConnection(destination)){
			DBOperationsDAO dao = new DBOperationsDAO();
			for(String objectType : objectMap.keySet()) {
				List<DBObject> objectList = objectMap.get(objectType);
				if(objectList!=null && objectList.size()>0) {
					for (DBObject dbObject: objectList) {
						System.out.println(dao.performDDLOperations(con, dbObject.getBody()));
					}
				}
			}
			
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
