package edu.stevens.cs.cs548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity

public class Provider implements Serializable {

	@Id long npi;
	private String name;
	private Clinic clinic;
	private List<Treatment> treatments;
	private ITreatmentDAO treatmentDAO;
	
	@Transient
	Logger logger = 
			Logger.getLogger("edu.stevens.cs.cs548.clinic.domain.Patient");
	
	private static final long serialVersionUID = 1L;

	public Provider() {
		super();
	}

	public Provider(long npi) {
		this.npi = npi;
	}
	
	public Provider(long npi, String name) {
		this.npi = npi;
		this.setName(name);
	}

	public long getNpi() {
		return npi;
	}

	public void setNpi(long npi) {
		this.npi = npi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "clinic_id", referencedColumnName = "id")
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	protected List<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(List<Treatment> treatments) {
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
		} else if (t.getProvider() != this) {
			logger.warning("Tried to access treatment " 
					+ tid + " not related to provider " + npi);
			throw new TreatmentException(
					"Treatment not for this provider: " + tid, tid);
		} else {
			t.visit(v);
		}
	}
	
	public void visitTreatments(ITreatmentVisitor v) {
		for (Treatment t : this.getTreatments()) {
			t.visit(v);
		}
	}
   
}
