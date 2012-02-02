package edu.stevens.cs.cs548.clinic.rest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs.cs548.clinic.service.ejb.IClinicBeanRemote;
import edu.stevens.cs.cs548.rest.representation.Representation;

@Path("/research")
public class ResearchResource {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;

    @EJB(beanName = "ClinicBean")
    IClinicBeanRemote clinic;
    
    /**
     * Default constructor. 
     */
    public ResearchResource() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Return a list of all treatment resources (URIs) associated with 
     * a given drug.
     * @param name
     * @return 
     */
    @GET
    @Path("/{drug-name}")
    @Produces("application/xml")
    public Response getTreatments(@PathParam("drug-name") String name) {
    	List<URI> treatmentURIs = new ArrayList<URI>();
    	List<Long> treatmentIds = clinic.getTreatmentsByDrug(name);
    	for (Long id : treatmentIds) {
    		treatmentURIs.add(URI.create(Representation.NAMESPACE 
	    										+ "treatment/" + id));
    	}
		return Response.ok(treatmentURIs).build();
    }

}