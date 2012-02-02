package edu.stevens.cs.cs548.clinic.research.domain;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import edu.stevens.cs.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs.cs548.clinic.domain.Treatment;
import edu.stevens.cs.cs548.clinic.domain.TreatmentDAO;

@NamedQuery(
		name = "findByName", 
		query = "select d from Drug d where d.name = :name"
)
public class DrugDAO {
	
	private EntityManager em;
	
	public DrugDAO(EntityManager em) {
		this.em = em;
	}
	
	public Drug addDrug(String name) {
		Drug drug = new Drug();
		drug.setName(name);
		em.persist(drug);
		return drug;
	}
	
	public Drug findByName(String name) {
		TypedQuery<Drug> query = 
				em.createNamedQuery("findByName", Drug.class)
					.setParameter("name", name).setMaxResults(1);
		try {
			Drug drug = query.getSingleResult();
			return drug;
		} catch (NoResultException e){
			return null;
		}
	}
	
	public void addDrugInfo(String name, long tid) {
		Drug drug = this.findByName(name);
		if (drug == null) 
			drug = this.addDrug(name);
		TreatmentDAO td = new TreatmentDAO(em);
		Treatment treatment = td.getTreatment(tid);
		String d = "D";
		if (d == treatment.getTreatmentType()) {
			Set<DrugTreatment> treatments = 
								drug.getTreatments();
			treatments.add((DrugTreatment)treatment);
			drug.setTreatments(treatments);
			em.persist(drug);
		}
	}

}
