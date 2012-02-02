package edu.stevens.cs.cs548.rest.representation;

import java.util.List;

import edu.stevens.cs.cs548.clinic.service.dto.PatientDTO;

public class PatientRepresentation extends PatientType {
	
	public List<LinkType> getLinks() {
		return this.getTreatments().getLink();
	}

	public PatientRepresentation(PatientDTO dto) {
		this.name = dto.name;
		this.dob = dto.dob;
		
		PatientType.Treatments treatments = 
									new PatientType.Treatments();
		List<LinkType> links = treatments.getLink();
		
		for (Long tid : dto.treatmentIds) {
			String treatmentURI = Representation.NAMESPACE + 
				"/patient/" + dto.getId() + "/treatment/" + tid;
			LinkType link = new LinkType();
			link.setUrl(treatmentURI);
			link.setRelation(Representation.RELATION_TREATMENT);
			link.setMediaType(Representation.MEDIA_TYPE);
			links.add(link);
		}
	}
	
	public PatientRepresentation() {
		
	}
}
