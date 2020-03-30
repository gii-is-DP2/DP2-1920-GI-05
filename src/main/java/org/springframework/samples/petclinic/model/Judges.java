package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Judges {
	
	private List<Judge> judges;
	
	@XmlElement
	public List<Judge> getJugdeList() {
		if (judges == null) {
			judges = new ArrayList<>();
		}
		return judges;
	}
}
