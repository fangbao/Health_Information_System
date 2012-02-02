package edu.stevens.cs.cs548.clinic.service.ejb;

import java.util.List;

import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.stevens.cs.cs548.clinic.domain.Clinic;
import edu.stevens.cs.cs548.clinic.domain.ClinicException;
import edu.stevens.cs.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs.cs548.clinic.domain.PatientDAO;

/**
 * Session Bean implementation class ClinicDAOBean
 */
@Stateless(name = "ClinicDAO")
public class ClinicDAOBean implements IClinicDAOLocal {

    /**
     * Default constructor. 
     */
    public ClinicDAOBean() {
        // TODO Auto-generated constructor stub
    }

    @PersistenceContext(unitName = "ClinicDomainModel")
    private EntityManager em;
	private IPatientDAO patientDAO;
	
	@PostActivate
	public void initialize() {
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
	
	@Override
	public void addClinic(Clinic clinic) throws ClinicException {
		// TODO
	}
}
