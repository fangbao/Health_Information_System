package edu.stevens.cs.cs548.clinic.rest.resource;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs.cs548.clinic.service.dto.Treatment;
import edu.stevens.cs.cs548.clinic.service.ejb.ClinicServiceException;
import edu.stevens.cs.cs548.clinic.service.ejb.IClinicBeanRemote;
import edu.stevens.cs.cs548.clinic.service.ejb.IPatientRemote;
import edu.stevens.cs.cs548.rest.representation.PatientRepresentation;
import edu.stevens.cs.cs548.rest.representation.Representation;
import edu.stevens.cs.cs548.rest.representation.TreatmentRepresentation;

@Path("/patient")
public class PatientResource {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;

    @EJB(beanName = "ClinicBean")
    IClinicBeanRemote clinic;
    
    @EJB(beanName = "PatientBean")
    IPatientRemote patient;
    
    /**
     * Default constructor. 
     */
    public PatientResource() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 1. Obtaining a list of URIs for patient resources, given a patient 
     * name and date of birth.
     * GET /clinic/rest/patient?name=name&dob=birthdate HTTP/1.1
     * @param name
     * @param dob
     * @return
     */
    @GET
    @Produces("application/xml")
    public Response getPatients(@QueryParam("name") String name, 
    							@QueryParam("dob") String dob) {
    	List<URI> patientURIs = new ArrayList<URI>();
		try {
			Date date = new SimpleDateFormat("mm/dd/yyyy").parse(dob);
	    	List<PatientDTO> patientDTOs = clinic.getPatients(name, date);
	    	for (PatientDTO p : patientDTOs) {
	    		URI uri = URI.create(Representation.NAMESPACE 
	    										+ "patient/" + p.getId());
	    		patientURIs.add(uri);
	    	}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(patientURIs).build();
    }

    /**
     * 2. Obtaining a single patient representation, given a patient resource 
     * URI. An HTTP response code of 403 (“Not found”) occurs if there is no 
     * patient for the specified patient identifier.
     * GET /clinic/rest/patient/patient-­‐id HTTP/1.1
     * @param patientId
     * @return
     */
    @GET
    @Path("/{patientId}")
    @Produces("application/xml")
    public Response getPatient
    						(@PathParam("patientId") String patientId) {
    	long pid = Long.parseLong(patientId);
    	PatientDTO patientDTO = clinic.getPatient(pid);
    	if (patientDTO != null) {
	    	PatientRepresentation patientRep = 
	    							new PatientRepresentation(patientDTO);
	        return Response.ok(patientRep).build();
    	} else {
    		return Response.status(404).build();
    	}
    }
    
    /**
     * 5. Obtaining a treatment representation given a treatment URI. Recall 
     * that the patient and provider representations include URIs for the 
     * treatments to which they are related. Requests for treatment information 
     * are based on those treatment URIs.
     * @param patientId
     * @param treatmentId
     * @return
     */
    @GET
    @Path("/{patientId}/treatment/{treatmentId}")
    @Produces("application/xml")
    public Response getTreatment
    		(@PathParam("patientId") String patientId,
    		 @PathParam("treatmentId") String treatmentId) {
    	long pid = Long.parseLong(patientId);
    	long tid = Long.parseLong(treatmentId);
    	Treatment treatmentDTO = patient.getTreatment(pid, tid);
    	if (treatmentDTO != null) {
	    	TreatmentRepresentation treatmentRep = 
	    					new TreatmentRepresentation(treatmentDTO);
	        return Response.ok(treatmentRep).build();
    	} else {
    		return Response.status(404).build();
    	}
    }

    /**
     * 6. Adding a patient to a clinic. The operation takes a patient 
     * representation (with no links to treatments) and returns a new 
     * patient URI in the response header. The HTTP response code should 
     * be 201 (“Created”), with the URI for the new patient resource in 
     * the Location response header.
     * POST /clinic/rest/patient HTTP/1.1
     * @param patient
     * @return
     */
    @POST
    @Consumes("application/xml")
    public Response addPatient(PatientRepresentation patient) {
    	try {
			clinic.addPatient(patient.getName(), patient.getDob());
	    	return Response.created(null).build();
		} catch (ClinicServiceException e) {
			throw new WebApplicationException();
		}
    }
    
    /**
     * 8. Adding a treatment for a patient. Define this as a POST operation 
     * on the resource for that patient. The input representation is a 
     * treatment representation that includes links to both the patient and 
     * provider resources for that treatment.
     * @param treatment
     * @return
     */
    @PUT
    @Consumes("application/xml")
    public Response addTreatment(TreatmentRepresentation treatment) {
    	long pid = 0; // TODO: get the patient id
		long npi = 0; // TODO: get the provider id
		if (treatment.getDrug() != null) {
			patient.addDrugTreatment(pid, 
					npi, 
					treatment.getDrug().getName(), 
					treatment.getDrug().getDosage());
		} else if (treatment.getSurgery() != null) {
			patient.addSurgery(pid, npi, 
					treatment.getSurgery().getDateOfSurgery());
		} else if (treatment.getRadiology() != null) {
			List<Date> datesOfRad = new ArrayList<Date>();
			List<String> dates = treatment.getRadiology().getDatesOfRad();
			for (String d : dates) {
				Date date;
				try {
					date = new SimpleDateFormat("mm/dd/yyyy").parse(d);
					datesOfRad.add(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			patient.addRadiology(pid, npi, datesOfRad);
		}
		return Response.created(null).build();
    }
}