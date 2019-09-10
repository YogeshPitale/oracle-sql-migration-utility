package org.migrato;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.migrato.service.AutomationService;
import org.migrato.service.impl.AutmationServiceImpl;
import org.migrato.util.FileUtils;
import org.migrato.util.ResourceReader;

public class MigrateObjects {

	public static void main(String[] args) {

		final File folder = new File(ResourceReader.getProperties().getProperty("INSTALL_SCRIPT_FOLDER_PATH"));
		  if(folder.listFiles()==null) { 
			  System.out.println("Oops.. Invalid Folder!"); 
			  System.exit(0);
		  }
		  
		  if(args==null || (args!=null && args.length<2)){
			  System.out.println("Please enter source & destination enviroment for migration");
			  System.exit(0);
		  }
		 FileUtils.populateDirectoryMap(folder);
	
		/*
		 * String sourceEnoviroment =
		 * ResourceReader.getProperties().getProperty("SOURCE_ENVIROMENT"); String
		 * destinationEnoviroment =
		 * ResourceReader.getProperties().getProperty("DESTINATION_ENVIROMENT");
		 */
		 
		 AutomationService automationService = new AutmationServiceImpl();
		 try {
			automationService.performMigration(args[0],args[1]);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
