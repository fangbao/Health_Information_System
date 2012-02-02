package edu.stevens.cs.cs548.clinic.service.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
		namespace = "http://www.example.org/health/billing", 
		name = "billing"
)
public class TreatmentBillingDTO {
	
	@XmlElement(name = "billing_id")
	public long id;
	
	@XmlElement
	public float amount;
	
	@XmlElement
	public long treatmentId;

	public TreatmentBillingDTO() {
		
	}
	
	public TreatmentBillingDTO(long id, 
							   float amount, 
							   long treatmentId) {
		this.id = id;
		this.amount = amount;
		this.treatmentId = treatmentId;
	}
	
	public long getId() {
		return id;
	}
}
