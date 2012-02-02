package edu.stevens.cs.cs548.rest.representation;

import edu.stevens.cs.cs548.clinic.service.dto.Treatment;

public class TreatmentRepresentation extends TreatmentType {

	public TreatmentRepresentation() {
		
	}
	
	public TreatmentRepresentation(Treatment dto) {
		// TODO: 
	}

	public LinkType getPatientLink() {
		return this.getPatient();
	}
	
	public LinkType getProviderLink() {
		return this.getProvider();
	}
	
}
