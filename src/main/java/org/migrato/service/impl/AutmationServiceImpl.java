package org.migrato.service.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.migrato.dao.DBOperationsDAO;
import org.migrato.dao.connectionfactory.impl.ConnectionProvider;
import org.migrato.enums.DBObjectTypes;
import org.migrato.enums.DirectoryNames;
import org.migrato.service.AutomationService;
import org.migrato.util.FileUtils;
import org.migrato.xmlbeans.DBObject;
import org.migrato.xmlbeans.ModifiedDBObject;
import org.migrato.xmlbeans.ObjectXMLBean;
import org.migrato.xmlbeans.ScriptXMLBean;

public class AutmationServiceImpl implements AutomationService{


	public void excecuteScript(String destinationEnviroments) throws JAXBException {
	
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
									executeScripts(scriptXMLBean,destinationEnviroments);	
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
	
	private static void executeScripts(ScriptXMLBean scriptXMLBean, String destinationEnviroment){
		if(scriptXMLBean!=null && scriptXMLBean.getSqlList().size()>0) {
			ConnectionProvider provider = new ConnectionProvider(destinationEnviroment);
			try (Connection con = provider.getConnection()){
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
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
		System.out.println("Reading file..");
		if(objectXMLBean!=null && objectXMLBean.getObjects()!=null && objectXMLBean.getObjects().size()>0) {
			for(DBObjectTypes objectType: DBObjectTypes.values()) {
				List<DBObject> objectList= extractObjects(sourceEnoviroment,objectXMLBean,objectType.toString());
				if(objectList.size()>0)
					objectMap.put(objectType.toString(),objectList);
			}
			exportObjects(objectMap,destinationEnoviroment);
		}	
	}
	
	private List<DBObject> extractObjects(String source, ObjectXMLBean objectXMLBean, String objectType){
		List<DBObject> dbObectList = new ArrayList<>(); 
		ConnectionProvider provider = new ConnectionProvider(source);
		try (Connection con = provider.getConnection()){
			String objectName =objectXMLBean.getObjectByType(objectType).get().getValues().trim();// objectXMLBean.getObjectByType(objectType).trim();
			if(objectName!=null && !objectName.trim().isEmpty()) {
				if(objectName.indexOf(";")!=-1) {
					String []dbObjects =objectName.split(";");
					for(String currentDbObject : dbObjects) {
						if(currentDbObject!=null && !currentDbObject.isEmpty()) {
						
							System.out.println("Extracting Object DDL for: " +currentDbObject);
							if(objectType!=null && objectType.equalsIgnoreCase(DBObjectTypes.PACKAGE.toString())) {
								String objectCode = "CREATE OR REPLACE "+getObjectCode(currentDbObject,DBObjectTypes.PACKAGE.toString(),provider,con);
								DBObject dbObject = new DBObject(currentDbObject.trim(),objectCode,objectType);
								dbObectList.add(dbObject);
							
								objectCode = "CREATE OR REPLACE "+getObjectCode(currentDbObject,DBObjectTypes.PACKAGE_BODY.toString().replace("_"," "),provider,con);
								dbObject = new DBObject(currentDbObject.trim(),objectCode,objectType);
								dbObectList.add(dbObject);
							}else {
								String objectCode = getObjectCode(currentDbObject,objectType,provider,con);
								DBObject dbObject = new DBObject(currentDbObject.trim(),objectCode,objectType);
								dbObectList.add(dbObject);
							}
						}
					}
				}else {
					String objectCode = getObjectCode(objectXMLBean.getObjectByType(objectType).get().getValues().trim(),objectType,provider,con); 
					DBObject dbObject = new DBObject(objectName,objectCode,objectType);
					dbObectList.add(dbObject);
				}
			}
			
		} catch (PropertyException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dbObectList;
	}
	
	
	private String getObjectCode(String objectName, String objectType, ConnectionProvider provider, Connection con) throws SQLException {
		DBOperationsDAO dao = new DBOperationsDAO();
		
		  if(DBObjectTypes.requiresDDL(objectType)) return
				  dao.getObjectDDL(objectName, objectType, provider, con); 
		  else 
			  return dao.getObjectCode(objectName, objectType,provider.getDbUSer(), con);
		 
		//return cleanUp(dao.getObjectDDL(objectName, objectType, provider, con),provider.getDbUSer());
		
	}
	
	/*
	 * private String cleanUp(String ddl, String source, String destination) {
	 * 
	 * String modfiedDDL =ddl.replace("\"","'"); modfiedDDL
	 * =modfiedDDL.replace("'"+source+"'.", ""); //modfiedDDL
	 * =modfiedDDL.substring(0, modfiedDDL.lastIndexOf(")")+1);
	 * modfiedDDL=modfiedDDL.replace("'", "\""); return modfiedDDL; }
	 */
	
	private void exportObjects(Map<String,List<DBObject>> objectMap, String destination) {
		ConnectionProvider provider = new ConnectionProvider(destination);
		System.out.println("Establishing connection to destination: " +destination);
		long time=System.currentTimeMillis();
		try (Connection con = provider.getConnection()){
			System.out.println("Established connection in:" +(System.currentTimeMillis()-time)+"ms");
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
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public ObjectXMLBean extractObjectsFromSource(String sourceEnviroment,String date) throws PropertyException, SQLException {
			DBOperationsDAO dao = new DBOperationsDAO();
			ObjectXMLBean objectBean = new ObjectXMLBean();
			ConnectionProvider provider = new ConnectionProvider(sourceEnviroment);
			try(Connection con = provider.getConnection()){
			for(DBObjectTypes objectType : DBObjectTypes.values()) {
				ModifiedDBObject modifiedDBObject = new ModifiedDBObject();
				modifiedDBObject.setType(objectType.toString());
				modifiedDBObject.setValues(dao.getObjectsListByType(date, objectType.toString(),provider.getDbUSer(), con));
				objectBean.getObjects().add(modifiedDBObject);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objectBean;
	}
}
