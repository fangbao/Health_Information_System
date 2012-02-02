package edu.stevens.cs.cs548.clinic.web.soap;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.stevens.cs.cs548.clinic.service.dto.PatientDTO;

@WebService(name = "Clinic", 
			serviceName = "ClinicService", 
			targetNamespace = "http://www.example.org/clinic/web/soap")
public interface IClinic {
	
	@WebMethod
	// @Oneway -- cannot use because an exception may be thrown
	public void addPatient(String name, Date dob)
		throws ClinicWebFault;
	
	@WebMethod
	public List<PatientDTO> getPatientByName(String name, Date dob);
	
	@WebMethod
	public PatientDTO getPatientById(long id);
	
	@WebMethod
	public List<Long> getPatients();
	
	/**
	 * For testing purposes.
	 */
	
	@WebMethod
	public String getClinicName();
	
	@WebMethod
	public void ping();

}
