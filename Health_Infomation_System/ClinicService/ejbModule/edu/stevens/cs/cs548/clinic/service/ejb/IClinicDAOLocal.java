package edu.stevens.cs.cs548.clinic.service.ejb;
import javax.ejb.Local;

import edu.stevens.cs.cs548.clinic.domain.Clinic;
import edu.stevens.cs.cs548.clinic.domain.ClinicException;

@Local
public interface IClinicDAOLocal {

	public void addClinic(Clinic clinic) throws ClinicException;

	public Clinic getClinic(String name);

}
