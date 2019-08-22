package com.avaya.dbautomation.xmlbeans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name="dbscript")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScriptXMLBean extends GenericBean implements Comparator<ScriptXMLBean> {

	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "description")
	private String description;
	
	@XmlElement(name="sql")
	private List<SQLXMLBean> sqlList;
	
	@XmlElement(name="sequence")
	private int sequence;
	
    public void add(SQLXMLBean sqlBean) {
        if (this.sqlList == null) {
            this.sqlList = new ArrayList<SQLXMLBean>();
        }
        this.sqlList.add(sqlBean);
    }

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<SQLXMLBean> getSqlList() {
		return sqlList;
	}

	public int getSequence() {
		return sequence;
	}

	public ScriptXMLBean(String name, String description, List<SQLXMLBean> sqlList, int sequence) {
		super();
		sqlList = new ArrayList<>();
		this.name = name;
		this.description = description;
		this.sqlList = sqlList;
		this.sequence = sequence;
	}

	public ScriptXMLBean() {
		super();
	}
	
	@Override
	public int compare(ScriptXMLBean o1, ScriptXMLBean o2) {
		return o1.sequence-o2.sequence;
	}
	

	@Override
	public String toString() {
		return "ScriptXMLBean [name=" + name + ", description=" + description + ", sqlList=" + sqlList + ", sequence="
				+ sequence + "]";
	}

}
