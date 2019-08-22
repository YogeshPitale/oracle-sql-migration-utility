package com.avaya.service;

import javax.xml.bind.JAXBException;

public interface AutomationService {
	public void excecuteScript() throws JAXBException;
	public void performMigration(String sourceEnoviroment, String destinationEnoviroment) throws JAXBException;
}
