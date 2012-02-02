package edu.stevens.cs.cs548.clinic.service.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.example.org/health/patient", 
				name = "patient")
public class ProviderDTO {
	
	public ProviderDTO() {
		
	}
	
	@XmlElement
	public long npi;

	public long getNpi() {
		return npi;
	}

	@XmlElement
	public String name;
	
	@XmlElement
	public List<Long> treatmentIds;

	public ProviderDTO(long id, String name, 
					  List<Long> treatmentIds) {
		this.npi = id;
		this.name = name;
		this.treatmentIds = treatmentIds;
	}

}
