package edu.stevens.cs.cs548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@NamedQuery(
		name = "findByName", 
		query = "select c from Clinic c " +
					"where c.name = :name"
)
public class ClinicDAO implements IClinicDAO {

	private EntityManager em;
	private IPatientDAO patientDAO;
	
	public ClinicDAO() {
		EntityManagerFactory emf = 
			Persistence
				.createEntityManagerFactory("ClinicDomainModel");
		this.em = emf.createEntityManager();
		this.patientDAO = new PatientDAO(em);
	}
	
	public List<Clinic> getClinics(String name) {
		TypedQuery<Clinic> query = 
				em.createNamedQuery("findByName", Clinic.class)
					.setParameter("name", name)
					.setMaxResults(1);
		List<Clinic> clinics = query.getResultList();
		return clinics;
	}
	
	@Override
	public void addClinic(Clinic clinic) throws ClinicException {
		
	}
	
	@Override
	public Clinic getClinic(String name) {
		List<Clinic> clinics = getClinics(name);
		if (clinics.size() == 1) {
			Clinic clinic = clinics.get(0);
			clinic.setPatientDAO(this.patientDAO);
			return clinic;
		} else {
			return null;
		}
	}
}
