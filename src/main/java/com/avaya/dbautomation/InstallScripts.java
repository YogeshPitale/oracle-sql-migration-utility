package com.avaya.dbautomation;

import java.io.File;

import javax.xml.bind.JAXBException;

import com.avaya.service.AutomationService;
import com.avaya.service.impl.AutmationServiceImpl;
import com.avaya.util.FileUtils;
import com.avaya.util.ResourceReader;

public class InstallScripts {
	
	public static void main(String[] args) throws JAXBException {
		final File folder = new File(ResourceReader.getProperties().getProperty("INSTALL_SCRIPT_FOLDER_PATH"));
		  if(folder.listFiles()==null) { 
			  System.out.println("Oops.. Invalid Folder!"); 
			  System.exit(0);
		  }
		 FileUtils.populateDirectoryMap(folder);
		 AutomationService automationService = new AutmationServiceImpl();
		 automationService.excecuteScript();
	}

}
