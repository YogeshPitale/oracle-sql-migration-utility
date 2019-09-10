package org.migrato.enums;

public enum DirectoryNames {
	
	preinstall,install,postinstall,migrate;
	
	public static boolean contains(String test) {

	    for (DirectoryNames c : DirectoryNames.values()) {
	        if (c.name().equalsIgnoreCase(test)) {
	            return true;
	        }
	    }

	    return false;
	}
}


