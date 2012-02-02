package edu.stevens.cs.cs548.rest.representation;

import java.util.List;

import edu.stevens.cs.cs548.clinic.service.dto.ProviderDTO;

public class ProviderRepresentation extends ProviderType {
	
	public List<LinkType> getLinks() {
		return this.getTreatments().getLink();
	}

	public ProviderRepresentation(ProviderDTO dto) {
		this.name = dto.name;
		
		PatientType.Treatments treatments = new PatientType.Treatments();
		List<LinkType> links = treatments.getLink();
		
		for (Long tid : dto.treatmentIds) {
			String treatmentURI = Representation.NAMESPACE + 
													"/treatment/" + tid;
			LinkType link = new LinkType();
			link.setUrl(treatmentURI);
			link.setRelation(Representation.RELATION_TREATMENT);
			link.setMediaType(Representation.MEDIA_TYPE);
			links.add(link);
		}
	}
	
	public ProviderRepresentation() {
		
	}
}
