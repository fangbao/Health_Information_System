package edu.stevens.cs.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;

@NamedQuery(
		name = "findByNameDob", 
		query = "select p from Patient p " +
					"where p.name = :name, " +
						  "p.dob = :dob, " +
						  "p.clinic = :clinic"
)
public class PatientDAO implements IPatientDAO {
	
	private EntityManager em;
	private ITreatmentDAO treatmentDAO;
	
	public PatientDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public Patient getPatient(long id) {
		Patient patient = em.find(Patient.class, id);
		if (patient != null) {
			patient.setTreatmentDAO(treatmentDAO);
		}
		return patient;
	}

	@Override
	public void addPatient(Patient patient) {
		if (patient != null) {
			em.persist(patient);
			patient.setTreatmentDAO(treatmentDAO);
		}
	}

	@Override
	public List<Patient> getPatients(String name, 
									 Date dob, 
									 Clinic clinic) {
		TypedQuery<Patient> query = 
				em.createNamedQuery("findByNameDob", Patient.class)
					.setParameter("name", name)
					.setParameter("dob", dob)
					.setParameter("clinic", clinic);
		List<Patient> patients = query.getResultList();
		for (Patient p : patients) {
			p.setTreatmentDAO(treatmentDAO);
		}
		return patients;
	}

}
