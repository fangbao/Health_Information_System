package edu.stevens.cs.cs548.clinic.domain;

import java.util.List;

public interface IProviderDAO {

	public Provider getProvider(long npi);
	
	public void addProvider(Provider provider);
	
	public List<Provider> getProviders(String name, Clinic clinic);
}