package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jugdes {
	
	private List<Jugde> judges;
	
	@XmlElement
	public List<Jugde> getJugdeList() {
		if (judges == null) {
			judges = new ArrayList<>();
		}
		return judges;
	}
}
