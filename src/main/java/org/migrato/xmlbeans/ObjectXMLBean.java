package org.migrato.xmlbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@XmlRootElement(name="migration")
@XmlAccessorType(XmlAccessType.FIELD)

public class ObjectXMLBean extends GenericBean  {


	public ObjectXMLBean() {
		this.objects= new ArrayList<ModifiedDBObject>();
	}

	@XmlElement(name = "objects")
	private List<ModifiedDBObject> objects;
	
	public Optional<ModifiedDBObject> getObjectByType(String objectType) {
		return this.getObjects().stream().filter(o->o.getType().equalsIgnoreCase(objectType)).findFirst();
	}
	
}
