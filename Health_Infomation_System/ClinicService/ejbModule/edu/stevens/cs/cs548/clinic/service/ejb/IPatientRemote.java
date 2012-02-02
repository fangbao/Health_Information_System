package edu.stevens.cs.cs548.clinic.service.ejb;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import edu.stevens.cs.cs548.clinic.service.dto.Treatment;

@Remote
public interface IPatientRemote {
	
	public Treatment getTreatment(long pid, long tid);
	
	public void addDrugTreatment(long pid, long npi, String drug, int dosage);
	
	public void addSurgery(long pid, long npi, Date date);
	
	public void addRadiology(long pid, long npi, List<Date> dates);
	
}
