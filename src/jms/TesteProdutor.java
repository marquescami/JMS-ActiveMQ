package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteProdutor {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);
		
		for(int i = 0; i < 1000; i ++) { 
		    Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
		    producer.send(message);
		}
		//Message message = session.createTextMessage("<pedido><id><123></id></pedido>");
		//producer.send(message);
				
		//new Scanner(System.in).nextLine(); // parar o programa para testar a conexao

		session.close(); // fecha conexões
		connection.close();
		context.close();
	}

}
