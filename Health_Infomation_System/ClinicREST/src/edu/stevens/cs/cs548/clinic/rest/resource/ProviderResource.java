package edu.stevens.cs.cs548.clinic.rest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs.cs548.clinic.service.ejb.ClinicServiceException;
import edu.stevens.cs.cs548.clinic.service.ejb.IClinicBeanRemote;
import edu.stevens.cs.cs548.rest.representation.ProviderRepresentation;
import edu.stevens.cs.cs548.rest.representation.Representation;

@Path("/provider")
public class ProviderResource {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;

    @EJB(beanName = "ClinicBean")
    IClinicBeanRemote clinic;
    
    /**
     * Default constructor. 
     */
    public ProviderResource() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 3. Obtaining a list of provider URIs, given a provider name.
     * GET /clinic/rest/provider?name=name HTTP/1.1
     * @param name
     * @return
     */
    @GET
    @Produces("application/xml")
    public Response getProviders(@QueryParam("name") String name) {
    	List<URI> uris = new ArrayList<URI>();
	    List<ProviderDTO> dtos = clinic.getProviders(name);
	    for (ProviderDTO p : dtos) {
	    	URI uri = URI.create(Representation.NAMESPACE 
	    										+ "provider/" + p.getNpi());
	    	uris.add(uri);
	    }
		return Response.ok(uris).build();
    }

    /**
     * 4. Obtaining a single provider representation, given a provider NPI.
     * GET /clinic/rest/provider/npi HTTP/1.1
     * An HTTP response code of 403 (“Not found”) occurs if there is no 
     * provider for the specified NPI.
     * @param providerId
     * @return
     */
    @GET
    @Path("/{providerId}")
    @Produces("application/xml")
    public Response getProvider
    						(@PathParam("providerId") String providerId) {
    	long npi = Long.parseLong(providerId);
    	ProviderDTO providerDTO = clinic.getProvider(npi);
    	if (providerDTO != null) {
	    	ProviderRepresentation providerRep = 
	    							new ProviderRepresentation(providerDTO);
	        return Response.ok(providerRep).build();
    	} else {
    		return Response.status(404).build();
    	}
    }

    /**
     * 7. Adding a provider to a clinic. The operation takes a provider 
     * representation (with no links to treatments) as an input representation.
     * PUT /clinic/rest/provider/npi HTTP/1.1
     * @param provider
     * @return
     */
    @PUT
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation provider) {
    	try {
			clinic.addProvider(provider.getNpi(), provider.getName());
	    	return Response.created(null).build();
		} catch (ClinicServiceException e) {
			throw new WebApplicationException();
		}
    }
}