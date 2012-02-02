package edu.stevens.cs.cs548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Patient
 *
 */
@Entity

public class Patient implements Serializable {

	@Id long id;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private Clinic clinic;
	private Set<Treatment> treatments;
	private ITreatmentDAO treatmentDAO;
	
	@Transient
	Logger logger = 
		Logger.getLogger("edu.stevens.cs.cs548.clinic.domain.Patient");

	private static final long serialVersionUID = 1L;

	public Patient() {
		super();
	}
	
	public Patient(String name, Date dob) {
		this.setName(name);
		this.setDob(dob);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id")
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	public ITreatmentDAO getTreatmentDAO() {
		return treatmentDAO;
	}

	public void setTreatmentDAO(ITreatmentDAO treatmentDAO) {
		this.treatmentDAO = treatmentDAO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	protected Set<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	public List<Long> getTreatmentIds() {
		List<Long> tids = new ArrayList<Long>();
		for (Treatment t : this.getTreatments()) {
			tids.add(t.getId());
		}
		return tids;
	}
	
	public void visitTreatment(long tid, ITreatmentVisitor v) 
			throws TreatmentException {
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t == null) {
			throw new TreatmentException(
					"No such treatment in DB: " + tid, tid);
		} else if (t.getPatient() != this) {
			logger.warning("Tried to access treatment " 
					+ tid + " not related to patient " + id);
			throw new TreatmentException(
					"Treatment not for this patient: " + tid, tid);
		} else {
			t.visit(v);
		}
	}
	
	public void visitTreatments(ITreatmentVisitor v) {
		for (Treatment t : this.getTreatments()) {
			t.visit(v);
		}
	}
	
	// Important!
	protected long addTreatment(Treatment t) {
		treatmentDAO.addTreatment(t);
		Set<Treatment> ts = this.getTreatments();
		if (t.getPatient() != this) {
			t.setPatient(this);
		}
		ts.add(t);
		this.setTreatments(ts);
		return t.getId();
	}
	
	public long addDrugTreatment(long npi, String drug, int dosage) {
		return this.addTreatment(
				new DrugTreatment(new Provider(npi), drug, dosage));
	}
	
	public long addSurgery(long npi, Date dateOfSurgery) {
		return this.addTreatment(
				new Surgery(new Provider(npi), dateOfSurgery));
	}
	
	public long addRadiology(long npi, List<Date> datesOfRad) {
		return this.addTreatment(
				new Radiology(new Provider(npi), datesOfRad));
	}
}
