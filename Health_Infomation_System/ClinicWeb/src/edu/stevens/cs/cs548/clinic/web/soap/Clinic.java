package edu.stevens.cs.cs548.clinic.web.soap;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs.cs548.clinic.service.ejb.ClinicServiceException;
import edu.stevens.cs.cs548.clinic.service.ejb.IClinicBeanLocal;

@WebService(endpointInterface = 
				"edu.stevens.cs.cs548.web.soap.IClinic")
public class Clinic implements IClinic {
	
	@EJB(beanName = "ClinicBean")
	IClinicBeanLocal clinic;

	@Override
	public void addPatient(String name, Date dob) 
			throws ClinicWebFault {
		try {
			clinic.addPatient(name, dob);
		} catch (ClinicServiceException e) {
			throw new ClinicWebFault(e.getMessage());
		}

	}

	@Override
	public List<PatientDTO> getPatientByName(String name, Date dob) {
		return clinic.getPatients(name, dob);
	}

	@Override
	public PatientDTO getPatientById(long id) {
		return clinic.getPatient(id);
	}

	@Override
	public List<Long> getPatients() {
		return clinic.getPatients();
	}

	@Override
	public String getClinicName() {
		return clinic.getClinicName();
	}

	@Override
	public void ping() {
		return;
	}

}
