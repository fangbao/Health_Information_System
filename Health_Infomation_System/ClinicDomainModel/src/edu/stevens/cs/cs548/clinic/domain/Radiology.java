package edu.stevens.cs.cs548.clinic.domain;

import edu.stevens.cs.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@DiscriminatorValue("R")
public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Radiology() {
		super();
	}

	public Radiology(Provider provider, List<Date> datesOfRad) {
		// TODO Auto-generated constructor stub
	}
   
}
