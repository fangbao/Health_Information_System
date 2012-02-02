package edu.stevens.cs.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs.cs548.clinic.service.dto.TreatmentBillingDTO;

public interface IClinicBean {

	public List<Long> getPatients();

	public PatientDTO getPatient(long id);

	public List<PatientDTO> getPatients(String name, Date dob);

	public void addPatient(String name, Date dob) 
			throws ClinicServiceException;
	
	public List<Long> getProviders();

	public ProviderDTO getProvider(long npi);

	public List<ProviderDTO> getProviders(String name);

	public void addProvider(long npi, String name) 
			throws ClinicServiceException;
	
	public String getClinicName();
	
	public TreatmentBillingDTO getTreatmentBilling(long tid);
	
	public List<Long> getTreatmentsByDrug(String name);
}
