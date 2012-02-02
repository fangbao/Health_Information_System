package edu.stevens.cs.cs548.clinic.service.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: BillingBean
 *
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", 
				propertyValue = "javax.jms.Topic"
		) }, 
		mappedName = "topic/treatmentTopic")
public class BillingBean implements MessageListener {

    /**
     * Default constructor. 
     */
	public BillingBean() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        TextMessage msg = (TextMessage)message;
    }

}
