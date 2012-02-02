package edu.stevens.cs.cs548.clinic.service.ejb;
import javax.ejb.Local;

import edu.stevens.cs.cs548.clinic.domain.Patient;

@Local
public interface IClinicLocal {
	
	public Patient getPatientEntity(long id);
	
}
