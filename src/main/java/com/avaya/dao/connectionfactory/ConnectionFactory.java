package com.avaya.dao.connectionfactory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.bind.PropertyException;

public interface ConnectionFactory {
	public Connection getConnection(String type) throws PropertyException,SQLException;
}
