package org.migrato.service;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.migrato.xmlbeans.ObjectXMLBean;

public interface AutomationService {
	public void excecuteScript(String destinationEnviroment) throws JAXBException;
	public void performMigration(String sourceEnoviroment, String destinationEnoviroment) throws JAXBException;
	public ObjectXMLBean extractObjectsFromSource(String sourceEnviroment,String date) throws PropertyException, SQLException;
}
