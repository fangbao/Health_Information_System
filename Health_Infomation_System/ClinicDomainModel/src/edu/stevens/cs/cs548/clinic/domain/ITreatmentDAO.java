package edu.stevens.cs.cs548.clinic.domain;

public interface ITreatmentDAO {
	
	public Treatment getTreatment(long id);
	
	public void addTreatment(Treatment treatment);
	
}
