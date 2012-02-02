package edu.stevens.cs.cs548.clinic.domain;

import edu.stevens.cs.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DrugTreatment
 *
 */
@Entity
@DiscriminatorValue("D")
public class DrugTreatment extends Treatment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public DrugTreatment() {
		super();
	}

	public DrugTreatment(Provider provider, String drug, int dosage) {
		// TODO Auto-generated constructor stub
	}
	
	
}
