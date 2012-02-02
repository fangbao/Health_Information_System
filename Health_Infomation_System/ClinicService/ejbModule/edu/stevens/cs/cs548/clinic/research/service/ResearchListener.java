package edu.stevens.cs.cs548.clinic.research.service;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs.cs548.clinic.research.domain.DrugDAO;
import edu.stevens.cs.cs548.clinic.service.dto.DrugTreatmentType;

/**
 * Message-Driven Bean implementation class for: ResearchListener
 *
 */
@MessageDriven(
		activationConfig = { 
			@ActivationConfigProperty(
				propertyName = "destinationType", 
				propertyValue = "javax.jms.Topic"
			),
			@ActivationConfigProperty(
				propertyName = "messageSelector", 
				propertyValue = "type = drug"
			) 
		}, 
		// TODO: selector property?
		mappedName = "jms/clinic/Treatment")
public class ResearchListener implements MessageListener {

    /**
     * Default constructor. 
     */
    public ResearchListener() {
        // TODO Auto-generated constructor stub
    }
    
    Logger logger = Logger.getLogger("edu.stevens.cs.cs548.clinic." +
			"service.ejb.ResearchListener");
    
    @PersistenceContext(unitName = "ClinicDomain")
    private EntityManager em;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	ObjectMessage objMessage = (ObjectMessage)message;
        try {
			DrugTreatmentType treatment = 
					(DrugTreatmentType)objMessage.getObject();
			DrugDAO drugDAO = new DrugDAO(em);
			
			drugDAO.addDrugInfo(treatment.getName(), 
								treatment.getTid());
		} catch (JMSException e) {
			logger.severe("Error getting message: " + e);
		}
    }

}
