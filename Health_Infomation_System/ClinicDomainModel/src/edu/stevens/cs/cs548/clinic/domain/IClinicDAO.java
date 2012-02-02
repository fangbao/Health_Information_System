package edu.stevens.cs.cs548.clinic.domain;

public interface IClinicDAO {

	public void addClinic(Clinic clinic) throws ClinicException;

	public Clinic getClinic(String name);

}
