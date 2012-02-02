package edu.stevens.cs.cs548.clinic.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.stevens.cs.cs548.clinic.billing.domain.TreatmentBilling;
import edu.stevens.cs.cs548.clinic.domain.Clinic;
import edu.stevens.cs.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs.cs548.clinic.domain.Patient;
import edu.stevens.cs.cs548.clinic.domain.Provider;
import edu.stevens.cs.cs548.clinic.research.domain.Drug;
import edu.stevens.cs.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs.cs548.clinic.service.dto.TreatmentBillingDTO;

/**
 * Session Bean implementation class ClinicBean
 */
@Stateless(name = "ClinicBean")
public class ClinicBean implements IClinicBeanRemote, 
							IClinicBeanLocal, IClinicLocal {
	
    /**
     * Default constructor. 
     */
    public ClinicBean() {
        
    }
    
	@Resource(name = "ClinicName")
	private String clinicName;
	
	@EJB(beanName = "ClinicDAO")
	private IClinicDAOLocal clinicDAO;

	private Clinic clinic;
	
	@PostConstruct
	public void initialize() {
		clinic = clinicDAO.getClinic(this.clinicName);
	}
	
	@Override
	public String getClinicName(){
		return clinicName;
	}

	@Override
    public List<Long> getPatients() {
    	Set<Patient> patients = clinic.getPatients();
    	List<Long> pids = new ArrayList<Long>();
    	for (Patient p : patients) {
    		pids.add(p.getId());
    	}
    	return pids;
    }
    
    @Override
    public PatientDTO getPatient(long id) {
    	Patient patient = clinic.getPatient(id);
    	return new PatientDTO(patient.getId(), 
    						  patient.getName(), 
    						  patient.getDob(), 
    						  patient.getTreatmentIds()); 
    }
    
    @Override
    public List<PatientDTO> getPatients(String name, Date dob) {
    	List<Patient> patients = clinic.getPatients(name, dob);
    	List<PatientDTO> dtos = new ArrayList<PatientDTO>();
    	for (Patient p : patients) {
    		PatientDTO dto = new PatientDTO(p.getId(), 
					 						p.getName(), 
					 						p.getDob(), 
					 						p.getTreatmentIds());
    		dtos.add(dto);
    	}
    	return dtos;
    }
    
    @Override
    public void addPatient(String name, Date dob) 
    		throws ClinicServiceException {
    	clinic.addPatient(name, dob);
    }

	@Override
	public Patient getPatientEntity(long id) {
		return clinic.getPatient(id);
	}

	@Override
	public List<Long> getProviders() {
		Set<Provider> providers = clinic.getProviders();
    	List<Long> pids = new ArrayList<Long>();
    	for (Provider p : providers) {
    		pids.add(p.getNpi());
    	}
    	return pids;
	}

	@Override
	public ProviderDTO getProvider(long npi) {
		Provider provider = clinic.getProvider(npi);
    	return new ProviderDTO(provider.getNpi(), 
    						   provider.getName(), 
    						   provider.getTreatmentIds());
	}

	@Override
	public List<ProviderDTO> getProviders(String name) {
		List<Provider> providers = clinic.getProviders(name);
    	List<ProviderDTO> dtos = new ArrayList<ProviderDTO>();
    	for (Provider p : providers) {
    		ProviderDTO dto = new ProviderDTO(p.getNpi(), 
					 						  p.getName(), 
					 						  p.getTreatmentIds());
    		dtos.add(dto);
    	}
    	return dtos;
	}

	@Override
	public void addProvider(long npi, String name)
			throws ClinicServiceException {
		clinic.addProvider(npi, name);
	}

	@Override
	public TreatmentBillingDTO getTreatmentBilling(long tid) {
		TreatmentBilling treatmentBilling =
				clinic.getTreatmentBilling(tid);
		if (treatmentBilling != null) {
			return new TreatmentBillingDTO(
						treatmentBilling.getId(), 
						treatmentBilling.getAmount(), 
						treatmentBilling.getTreatment().getId());
		} else {
			return null;
		}
	}

	@Override
	public List<Long> getTreatmentsByDrug(String name) {
		Drug drug = clinic.getDrug(name);
		List<Long> treatmentIds = new ArrayList<Long>();
		Set<DrugTreatment> treatments = drug.getTreatments();
		for (DrugTreatment dt : treatments) {
			treatmentIds.add(dt.getId());
		}
		return treatmentIds;
	}
	
}
