package edu.stevens.cs.cs548.clinic.rest.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs.cs548.clinic.service.dto.TreatmentBillingDTO;
import edu.stevens.cs.cs548.clinic.service.ejb.IClinicBeanRemote;

@Path("/billing")
public class BillingResource {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;

    @EJB(beanName = "ClinicBean")
    IClinicBeanRemote clinic;
    
    /**
     * Default constructor. 
     */
    public BillingResource() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Return the charge associated with a treatment.
     * @param tid
     * @return
     */
    @GET
    @Path("/{treatment-id}")
    @Produces("application/xml")
    public Response getTreatmentBilling(
    							@PathParam("treatment-id") long tid) {
        TreatmentBillingDTO dto = clinic.getTreatmentBilling(tid);
        if (dto != null) {
        	return Response.ok(dto).build();
        } else {
        	return Response.status(404).build();
        }
    }

}