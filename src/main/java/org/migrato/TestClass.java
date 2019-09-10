package org.migrato;

public class TestClass {

	public static void main(String[] args) {
		String str = "CREATE TABLE \"DEV\".\"COUNTRIES\" \r\n" + 
				"   (	\"COUNTRY_ID\" CHAR(2) NOT NULL ENABLE, \r\n" + 
				"	\"COUNTRY_NAME\" VARCHAR2(40), \r\n" + 
				"	\"REGION_ID\" NUMBER, \r\n" + 
				"	 CONSTRAINT \"COUNTRY_C_ID_PK\" PRIMARY KEY (\"COUNTRY_ID\")\r\n" + 
				"  USING INDEX (CREATE UNIQUE INDEX \"DEV\".\"COUNTRY_C_ID_PKX\" ON \"DEV\".\"COUNTRIES\" (\"COUNTRY_ID\") \r\n" + 
				"  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS \r\n" + 
				"  TABLESPACE \"SYSTEM\" )  ENABLE, \r\n" + 
				"	 CONSTRAINT \"COUNTR_REG_FK\" FOREIGN KEY (\"REGION_ID\")\r\n" + 
				"	  REFERENCES \"DEV\".\"REGIONS\" (\"REGION_ID\") ENABLE\r\n" + 
				"   ) SEGMENT CREATION DEFERRED \r\n" + 
				"  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 \r\n" + 
				" NOCOMPRESS LOGGING\r\n" + 
				"  TABLESPACE \"SYSTEM\" ";
		str=str.replace("\"", "'");
		String rp ="'DEV'.";
		String finalstr = str.replace(rp, "");
		finalstr=finalstr.substring(0, finalstr.lastIndexOf(")")+1);
	//	System.out.println(str);
		System.out.println(finalstr);
	}

}
