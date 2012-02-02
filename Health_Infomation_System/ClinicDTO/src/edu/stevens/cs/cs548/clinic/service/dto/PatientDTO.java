package edu.stevens.cs.cs548.clinic.service.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.example.org/health/patient", 
				name = "patient")
public class PatientDTO {
	
	public PatientDTO() {
		
	}
	
	@XmlElement(name = "patient_id")
	public long id;

	@XmlElement
	public String name;
	
	@XmlElement
	public Date dob;
	
	@XmlElement
	public List<Long> treatmentIds;

	public PatientDTO(long id, String name, Date dob, 
					  List<Long> treatmentIds) {
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.treatmentIds = treatmentIds;
	}
	
	public long getId() {
		return id;
	}

}
