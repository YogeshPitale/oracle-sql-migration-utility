package com.avaya.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.avaya.dbautomation.xmlbeans.GenericBean;
import com.avaya.enums.DirectoryNames;

public class FileUtils {


	private static HashMap<String,List<File>> directoryMap = new HashMap<>();
	private static HashMap<String,Integer> directorySizeMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <T extends GenericBean> T readFile(Class<? extends GenericBean> clazz,File file) throws JAXBException{
		
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    GenericBean genericBeanXMLBean = (T) unmarshaller.unmarshal(file);
		return (T) genericBeanXMLBean;	
	}

	public static void populateDirectoryMap(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory() && DirectoryNames.contains(fileEntry.getName())) {
	        	getFilesFromCurrentFolder(fileEntry);
	        }
	    }
	}
	
	public static void getFilesFromCurrentFolder(File folderName) {
		List<File> fileList = new ArrayList<>();	
		for (final File fileEntry : folderName.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	fileList.add(fileEntry);
	        }
	    }
	  	directoryMap.put(folderName.getName(),fileList);
	  	directorySizeMap.put(folderName.getName(),fileList.size());
	}

	public static HashMap<String, List<File>> getDirectoryMap() {
		return directoryMap;
	}

	public static HashMap<String, Integer> getDirectorySizeMap() {
		return directorySizeMap;
	}
	
}
