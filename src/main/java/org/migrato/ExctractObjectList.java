package org.migrato;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.migrato.enums.ConnectionEnviroments;
import org.migrato.service.AutomationService;
import org.migrato.service.impl.AutmationServiceImpl;
import org.migrato.util.FileUtils;
import org.migrato.util.ResourceReader;
import org.migrato.xmlbeans.ObjectXMLBean;

public class ExctractObjectList {

	public static void main(String[] args) {

		AutomationService autoamtionService = new AutmationServiceImpl();
		try {
			if(args==null || args.length<1) {
				System.out.println("Please enter source enviroment for extraction. Avaialble choices > " + Arrays.asList(ConnectionEnviroments.values()));
				System.exit(0);
			}
				
			if(args==null || args.length<2) {
				System.out.println("ERROR : Please enter the start date(dd-mm-yyyy). Objects modified on or after this date will be extracted.");
				System.exit(0);
			}
			System.out.println("Enviroment: "+ args[0] );
			System.out.println("Objects modfied on or after: " + args[1]);
			ObjectXMLBean extactedObjectsBean=autoamtionService.extractObjectsFromSource(args[0],args[1]);
			String extractedFile=FileUtils.writeFile(extactedObjectsBean, ResourceReader.getProperties().getProperty("INSTALL_SCRIPT_FOLDER_PATH")+"\\migrate");
			System.out.println("Extraction complete: " + extractedFile);
		} catch (PropertyException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
