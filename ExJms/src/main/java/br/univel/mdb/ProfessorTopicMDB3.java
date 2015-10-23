package br.univel.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import br.unive.modelo.Professor;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/topic/professorTopic") }, mappedName = "java:/topic/professorTopic")
public class ProfessorTopicMDB3 implements MessageListener {
 
	public ProfessorTopicMDB3() {
 
	}
 
	public void onMessage(Message message) {
		System.out.println(getClass() + " Inicio");
		try {
 
			if (message instanceof TextMessage) {
				TextMessage mensagem = (TextMessage) message;
				System.out.println("Mensagem recebida: " + mensagem.getText());
			} else if (message instanceof ObjectMessage) {
				try {
					ObjectMessage obj = (ObjectMessage) message;
					Professor professor = (Professor) obj.getObject();
					System.out.println("Objeto Recebido: " + professor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getClass() + " Fim");
	}
}
