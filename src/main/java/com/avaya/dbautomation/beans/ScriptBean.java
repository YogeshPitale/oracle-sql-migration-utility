package com.avaya.dbautomation.beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScriptBean implements Comparator<ScriptBean> {

	private String name;
	
	private String description;
	
	private List<SQLBean> sqlList;
	
	private int sequence;
	
    public void add(SQLBean sqlBean) {
        if (this.sqlList == null) {
            this.sqlList = new ArrayList<SQLBean>();
        }
        this.sqlList.add(sqlBean);
    }

	public ScriptBean(String name, String description, List<SQLBean> sqlList, int sequence) {
		super();
		sqlList = new ArrayList<>();
		this.name = name;
		this.description = description;
		this.sqlList = sqlList;
		this.sequence = sequence;
	}

	public ScriptBean() {
	}
	
	@Override
	public int compare(ScriptBean o1, ScriptBean o2) {
		return o1.sequence-o2.sequence;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SQLBean> getSqlList() {
		return sqlList;
	}

	public void setSqlList(List<SQLBean> sqlList) {
		this.sqlList = sqlList;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "ScriptBean [name=" + name + ", description=" + description + ", sqlList=" + sqlList + ", sequence="
				+ sequence + "]";
	}

}
