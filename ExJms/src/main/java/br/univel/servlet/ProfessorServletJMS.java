package br.univel.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unive.modelo.Professor;




 
@WebServlet("/ProfessorServletJMS")
public class ProfessorServletJMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	@Resource(mappedName = "java:/topic/professorTopic")
	private Topic topic;
 
	@Inject
	@JMSConnectionFactory("java:/ConnectionFactory")
	private JMSContext context;
 
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
 
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(getClass() + "Inicio........");
		Professor professor = new Professor();
		professor.setMatricula(2221L);
		professor.setNome("Maria da Silva");
		enviarObjetoForma1(professor);
		System.out.println(getClass() + "Fim........");
 
		PrintWriter out = response.getWriter();
		out.print("<H1>Objeto enviado com sucesso! JMS TOPIC 2.0</H1>");
	}
 
	/**
	 * Envia um objeto complexo para a fila JMS
	 * 
	 * 
	 */
	public void enviarObjetoForma1(Professor professor) {
		try {
			ObjectMessage objMessage = context.createObjectMessage();
			objMessage.setObject(professor);
			context.createProducer().send(topic, objMessage);
 
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
