package com.avaya.dbautomation.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.avaya.enums.DBObjectTypes;

import lombok.Data;

@Data
@XmlRootElement(name="migration")
@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectXMLBean extends GenericBean  {


	public ObjectXMLBean() {}

	public ObjectXMLBean(String functions) {
		this.functions = functions;
	}

	@XmlElement(name = "functions")
	private String functions;
	
	@XmlElement(name = "procedures")
	private String procedures;
	
	@XmlElement(name = "types")
	private String types;

	@XmlElement(name = "triggers")
	private String triggers;

	@XmlElement(name = "packages")
	private String packages;

	public String getObjectByType(String type) {
		
		if(type.equalsIgnoreCase(DBObjectTypes.FUNCTION.toString()))
			return this.functions;
		else if(type.equalsIgnoreCase(DBObjectTypes.PROCEDURE.toString()))
			return this.procedures;
		if(type.equalsIgnoreCase(DBObjectTypes.TRIGGER.toString()))
			return this.triggers;
		if(type.equalsIgnoreCase(DBObjectTypes.TYPE.toString()))
			return this.types;
		if(type.equalsIgnoreCase(DBObjectTypes.PACKAGE.toString()))
			return this.packages;
		else
			return null;
	}
	
	public String getFunctions() {
		return functions;
	}

	public String getProcedures() {
		return procedures;
	}

	public void setProcedures(String procedures) {
		this.procedures = procedures;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTriggers() {
		return triggers;
	}

	public void setTriggers(String triggers) {
		this.triggers = triggers;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}
	
}
