package com.avaya.dbautomation;

import java.io.File;

import javax.xml.bind.JAXBException;

import com.avaya.service.AutomationService;
import com.avaya.service.impl.AutmationServiceImpl;
import com.avaya.util.FileUtils;
import com.avaya.util.ResourceReader;

public class MigrateObjects {

	public static void main(String[] args) {

		final File folder = new File(ResourceReader.getProperties().getProperty("INSTALL_SCRIPT_FOLDER_PATH"));
		  if(folder.listFiles()==null) { 
			  System.out.println("Oops.. Invalid Folder!"); 
			  System.exit(0);
		  }
		  
		 FileUtils.populateDirectoryMap(folder);
		 String sourceEnoviroment = ResourceReader.getProperties().getProperty("SOURCE_ENVIROMENT");
		 String destinationEnoviroment = ResourceReader.getProperties().getProperty("DESTINATION_ENVIROMENT");
	
		 AutomationService automationService = new AutmationServiceImpl();
		 try {
			automationService.performMigration(sourceEnoviroment,destinationEnoviroment);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
