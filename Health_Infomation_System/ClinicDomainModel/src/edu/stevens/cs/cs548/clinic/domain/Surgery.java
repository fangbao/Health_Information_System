package edu.stevens.cs.cs548.clinic.domain;

import edu.stevens.cs.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Surgery
 *
 */
@Entity
@DiscriminatorValue("S")
public class Surgery extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Surgery() {
		super();
	}

	public Surgery(Provider provider, Date dateOfSurgery) {
		// TODO Auto-generated constructor stub
	}
   
}
