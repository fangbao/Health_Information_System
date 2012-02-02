package edu.stevens.cs.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import edu.stevens.cs.cs548.clinic.billing.domain.TreatmentBilling;
import edu.stevens.cs.cs548.clinic.billing.domain.TreatmentBillingDAO;
import edu.stevens.cs.cs548.clinic.research.domain.Drug;
import edu.stevens.cs.cs548.clinic.research.domain.DrugDAO;

/**
 * Entity implementation class for Entity: Clinic
 *
 */
@Entity

public class Clinic implements Serializable {

	@Id long id;
	private String name;
	private Set<Patient> patients;
	private Set<Provider> providers;
	@Transient private IPatientDAO patientDAO;
	@Transient private IProviderDAO providerDAO;
	@Transient private TreatmentBillingDAO treatmentBillingDAO;
	@Transient private DrugDAO drugDAO;
	private static final long serialVersionUID = 1L;

	public Clinic() {
		super();
	}
	
	public Clinic(String name) {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "clinic")
	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Set<Provider> getProviders() {
		return providers;
	}

	public void setProviders(Set<Provider> providers) {
		this.providers = providers;
	}

	public void setPatientDAO(IPatientDAO patientDAO) {
		this.patientDAO = patientDAO;
	}

	public void setProviderDAO(IProviderDAO providerDAO) {
		this.providerDAO = providerDAO;
	}

	public void setTreatmentBillingDAO(
			TreatmentBillingDAO treatmentBillingDAO) {
		this.treatmentBillingDAO = treatmentBillingDAO;
	}

	public void setDrugDAO(DrugDAO drugDAO) {
		this.drugDAO = drugDAO;
	}

	public void addPatient(Patient patient) {
		patientDAO.addPatient(patient);
		Set<Patient> patients = this.getPatients();
		patients.add(patient);
		if (patient.getClinic() != this) {
			patient.setClinic(this);
		}
		this.setPatients(patients);
	}
	
	public void addPatient(String name, Date dob) {
		this.addPatient(new Patient(name, dob));
	}
	
	public Patient getPatient(long id) {
		Patient patient = patientDAO.getPatient(id);
		if (patient != null && patient.getClinic() == this) {
			return patient;
		} else {
			return null;
		}
	}
	
	public List<Patient> getPatients(String name, Date dob) {
		return patientDAO.getPatients(name, dob, this);
	}
	
	public void addProvider(Provider provider) {
		providerDAO.addProvider(provider);
		Set<Provider> patients = this.getProviders();
		patients.add(provider);
		if (provider.getClinic() != this) {
			provider.setClinic(this);
		}
		this.setProviders(providers);
	}
	
	public void addProvider(long npi, String name) {
		this.addProvider(new Provider(npi, name));
	}
	
	public Provider getProvider(long npi) {
		Provider provider = providerDAO.getProvider(npi);
		if (provider != null && provider.getClinic() == this) {
			return provider;
		} else {
			return null;
		}
	}
	
	public List<Provider> getProviders(String name) {
		return providerDAO.getProviders(name, this);
	}
   
	public TreatmentBilling getTreatmentBilling(long tid) {
		return treatmentBillingDAO.getBillingInfo(tid);
	}
	
	public Drug getDrug(String name) {
		return drugDAO.addDrug(name);
	}
}
