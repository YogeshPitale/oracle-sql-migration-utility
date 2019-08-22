package com.avaya.dbautomation.xmlbeans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="functions")
@XmlAccessorType(XmlAccessType.FIELD)
public class DBObjects {
	private List<DBObject> function;

	public DBObjects(List<DBObject> function) {
		this.function = function;
	}

	public DBObjects() {
	}

	public List<DBObject> getFunction() {
		return function;
	}
	
	
}
