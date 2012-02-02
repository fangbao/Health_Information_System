package edu.stevens.cs.cs548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;

@NamedQuery(
		name = "findByName", 
		query = "select p from Provider p " +
					"where p.name = :name, " +
						  "p.clinic = :clinic"
)
public class ProviderDAO implements IProviderDAO {
	
	private EntityManager em;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Provider getProvider(long npi) {
		Provider provider = em.find(Provider.class, npi);
		return provider;
	}

	@Override
	public void addProvider(Provider provider) {
		if (provider != null) {
			em.persist(provider);
		}
	}

	@Override
	public List<Provider> getProviders(String name, Clinic clinic) {
		TypedQuery<Provider> query = 
				em.createNamedQuery("findByName", Provider.class)
					.setParameter("name", name)
					.setParameter("clinic", clinic);
		return query.getResultList();
	}

}
