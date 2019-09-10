package org.migrato.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="function")
@XmlAccessorType(XmlAccessType.FIELD)
public class DBObject {

	private String name;
	private String body;
	private String type;

	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public DBObject(String name, String body,String type) {
		super();
		this.name = name;
		//if(type!=null && type.equalsIgnoreCase("TABLE"))
		/*
		 * if(body!=null && type!=null && !type.equalsIgnoreCase("TABLE") &&
		 * !type.equalsIgnoreCase("SEQUENCE") && !type.equalsIgnoreCase("INDEX") &&
		 * !type.equalsIgnoreCase("VIEW")) this.body = "CREATE OR REPLACE "+body; else
		 */
			this.body=body;
		this.type=type;
	}
	@Override
	public String toString() {
		return "DBObject [name=" + name + ", body=" + body + ", type=" + type + "]";
	}
	
}
