package org.migrato.beans;

public class SQLBean {
	
	private String ddl;
	
	private String dml;

	public SQLBean(String ddl, String dml) {
		super();
		this.ddl = ddl;
		this.dml = dml;
	}

	public SQLBean() {
	}

	public String getDdl() {
		return ddl;
	}

	public void setDdl(String ddl) {
		this.ddl = ddl;
	}

	public String getDml() {
		return dml;
	}

	public void setDml(String dml) {
		this.dml = dml;
	}

	@Override
	public String toString() {
		return "SQLBean [ddl=" + ddl + ", dml=" + dml + "]";
	}
	
}
