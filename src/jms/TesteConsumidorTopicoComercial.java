package jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import modelo.Pedido;

public class TesteConsumidorTopicoComercial {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.setClientID("comercial");

		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topico = (Topic) context.lookup("loja");

		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				ObjectMessage objectMessage = (ObjectMessage) message;

				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		});

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				TextMessage textMessage = (TextMessage) message;

				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		});

		new Scanner(System.in).nextLine(); // parar o programa para testar a conexao

		session.close(); // fecha conexões
		connection.close();
		context.close();
	}

}
