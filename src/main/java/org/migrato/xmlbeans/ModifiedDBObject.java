package org.migrato.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name="object")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifiedDBObject {
	
	@XmlElement(name="type")
	private String type;
	
	@XmlElement(name="values")
	private String values;

}
