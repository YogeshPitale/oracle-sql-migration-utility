package com.avaya.dbautomation.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name="sql")
@XmlAccessorType(XmlAccessType.FIELD)
public class SQLXMLBean {
	
	@XmlElement(name="ddl")
	private String ddl;
	
	@XmlElement(name="dml")
	private String dml;

	public String getDdl() {
		return ddl;
	}

	public String getDml() {
		return dml;
	}

	public SQLXMLBean(String ddl, String dml) {
		super();
		this.ddl = ddl;
		this.dml = dml;
	}

	public SQLXMLBean() {
		super();
	}

	@Override
	public String toString() {
		return "SQLBean [ddl=" + ddl + ", dml=" + dml + "]";
	}
	
}
