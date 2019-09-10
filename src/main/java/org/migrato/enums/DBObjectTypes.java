package org.migrato.enums;

public enum DBObjectTypes {
	TABLE,INDEX,SEQUENCE,TYPE,VIEW,FUNCTION,PROCEDURE,PACKAGE,PACKAGE_BODY,TRIGGER;
	
	public static boolean requiresDDL(String type) {
		if(type.equalsIgnoreCase(TABLE.toString())
		   || type.equalsIgnoreCase(INDEX.toString())
		   || type.equalsIgnoreCase(SEQUENCE.toString())
		   || type.equalsIgnoreCase(VIEW.toString())
		   || type.equalsIgnoreCase(PROCEDURE.toString())
		   || type.equalsIgnoreCase(TRIGGER.toString())
		   || type.equalsIgnoreCase(FUNCTION.toString())
		)
			return true;
		else 
			return false;
	}
	
}
