package edu.stevens.cs.cs548.clinic.service.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import edu.stevens.cs.cs548.clinic.domain.ITreatmentVisitor;
import edu.stevens.cs.cs548.clinic.domain.Patient;
import edu.stevens.cs.cs548.clinic.domain.TreatmentException;
import edu.stevens.cs.cs548.clinic.service.dto.DateAdapter;
import edu.stevens.cs.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs.cs548.clinic.service.dto.Treatment;

/**
 * Session Bean implementation class PatientBean
 * Business logic for patient service.
 */
@Stateless
public class PatientBean implements IPatientRemote {

    /**
     * Default constructor. 
     */
    public PatientBean() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB(beanName = "ClinicBean")
    private IClinicLocal clinic;

	@Override
	public Treatment getTreatment(long pid, long tid) {
		// TODO Auto-generated method stub
		Patient patient = clinic.getPatientEntity(tid);
		Treatment_PDO_to_DTO v = new Treatment_PDO_to_DTO();
		try {
			patient.visitTreatment(tid, v);
			return v.getTreatment();
		} catch (TreatmentException e) {
			return null;
		}
	}
	
	private static class Treatment_PDO_to_DTO 
		implements ITreatmentVisitor {

		private Treatment treatmentDTO;
		private ObjectFactory factory = new ObjectFactory();
		
		public Treatment getTreatment() {
			return treatmentDTO;
		}
		
		@Override
		public void visitDrugTreatment(String drug, int dosage) {
			DrugTreatmentType drugTreatment = 
					factory.createDrugTreatmentType();
			drugTreatment.setName(drug);
			drugTreatment.setDosage(dosage);
			treatmentDTO = factory.createTreatment();
			treatmentDTO.setDrug(drugTreatment);
		}

		@Override
		public void visitSergery(Date date) {
			SurgeryType surgery = factory.createSurgeryType();
			surgery.setDateOfSurgery(date);
			treatmentDTO = factory.createTreatment();
			treatmentDTO.setSurgery(surgery);
		}

		@Override
		public void visitRadiology(List<Date> datesOfRad) {
			RadiologyType radiology = factory.createRadiologyType();
			List<String> radDates = radiology.getDatesOfRad();
			for (Date d : datesOfRad) {
				radDates.add(DateAdapter.printDate(d));
			}
			treatmentDTO = factory.createTreatment();
			treatmentDTO.setRadiology(radiology);
		}
		
	}
	
	@Resource(mappedName = "jms/clinic/TreatmentPool")
	private ConnectionFactory treatmentConnFactory;
	
	@Resource(mappedName = "jms/clinic/Treatment")
	private Topic treatmentTopic;
	
	Logger logger = 
			Logger.getLogger("edu.stevens.cs.cs548.clinic." +
					"service.ejb.PatientBean");

	@Override
	public void addDrugTreatment(long pid, 
								 long npi, 
								 String drug, 
								 int dosage) {
		Patient patient = clinic.getPatientEntity(pid);
		long tid = patient.addDrugTreatment(npi, drug, dosage);
		
		Treatment treatment = new Treatment();
		DrugTreatmentType drugTreatment = new DrugTreatmentType();
		drugTreatment.setName(drug);
		drugTreatment.setDosage(dosage);
		treatment.setDrug(drugTreatment);
		treatment.setTid((int)tid);
		
		this.sendMessage(treatment);
	}

	@Override
	public void addSurgery(long pid, long npi, Date date) {
		Patient patient = clinic.getPatientEntity(pid);
		long tid = patient.addSurgery(npi, date);
		
		Treatment treatment = new Treatment();
		SurgeryType surgery = new SurgeryType();
		surgery.setDateOfSurgery(date);
		treatment.setSurgery(surgery);
		treatment.setTid((int)tid);
		
		this.sendMessage(treatment);
	}

	@Override
	public void addRadiology(long pid, long npi, List<Date> dates) {
		Patient patient = clinic.getPatientEntity(pid);
		long tid = patient.addRadiology(npi, dates);
		
		Treatment treatment = new Treatment();
		RadiologyType radiology = new RadiologyType();
		List<String> strDates = new ArrayList<String>();
		for (Date d : dates) {
			strDates.add(d.toString());
		}
		radiology.setDatesOfRad(strDates);
		treatment.setRadiology(radiology);
		treatment.setTid((int)tid);
		
		this.sendMessage(treatment);
	}

	private void sendMessage(Treatment treatment) {
		Connection treatmentConn = null;
		try {
			treatmentConn = 
					treatmentConnFactory.createConnection();
			Session session = 
					treatmentConn.createSession(true, 
									Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = 
					session.createProducer(treatmentTopic);
			
			ObjectMessage message = session.createObjectMessage();
			message.setObject(treatment);
			if (null != treatment.getDrug())
				message.setStringProperty("type", "drug");
			producer.send(message);
		} catch (JMSException e) {
			logger.severe("JMS Error: " + e);
		} finally {
			try {
				if (treatmentConn != null)
					treatmentConn.close();
			} catch (JMSException e) {
				logger.severe("Error closing JMS connection" + e);
			}
		}
	}
}
