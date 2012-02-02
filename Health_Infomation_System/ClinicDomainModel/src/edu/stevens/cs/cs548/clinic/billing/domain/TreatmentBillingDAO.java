package edu.stevens.cs.cs548.clinic.billing.domain;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import edu.stevens.cs.cs548.clinic.domain.Treatment;
import edu.stevens.cs.cs548.clinic.domain.TreatmentDAO;

@NamedQuery(
		name = "findByTreatmentId", 
		query = "select b from TreatmentBilling b " +
					"where b.treatment_id = :tid"
)
public class TreatmentBillingDAO {

	private EntityManager em;
	
	public TreatmentBillingDAO(EntityManager em) {
		this.em = em;
	}
	
	public void addBillingInfo(long tid, float amount) {
		TreatmentBilling tb = new TreatmentBilling();
		TreatmentDAO td = new TreatmentDAO(em);
		Treatment treatment = td.getTreatment(tid);
		tb.setTreatment(treatment);
		tb.setAmount(amount);
		em.persist(tb);
	}
	
	public TreatmentBilling getBillingInfo(long tid) {
		TypedQuery<TreatmentBilling> query = 
				em.createNamedQuery(
						"findByTreatmentId", 
						TreatmentBilling.class)
							.setParameter("tid", tid)
							.setMaxResults(1);
		try {
			TreatmentBilling treatmentBilling = 
					query.getSingleResult();
			return treatmentBilling;
		} catch (NoResultException e) {
			return null;
		}
		
	}
}
