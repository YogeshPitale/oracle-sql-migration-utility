package org.migrato;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.migrato.service.AutomationService;
import org.migrato.service.impl.AutmationServiceImpl;
import org.migrato.util.FileUtils;
import org.migrato.util.ResourceReader;

public class InstallScripts {
	
	public static void main(String[] args) throws JAXBException {
		 if(args==null || (args!=null && args.length<1)){
			  System.out.println("Please enter destination enviroment to install the scripts");
			  System.exit(0);
		  }
		 
		final File folder = new File(ResourceReader.getProperties().getProperty("INSTALL_SCRIPT_FOLDER_PATH"));
		  if(folder.listFiles()==null) { 
			  System.out.println("Oops.. Invalid Folder!"); 
			  System.exit(0);
		  }
		 FileUtils.populateDirectoryMap(folder,"migrate");
		 AutomationService automationService = new AutmationServiceImpl();
		 automationService.excecuteScript(args[0]);
	}

}
