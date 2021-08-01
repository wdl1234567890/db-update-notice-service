package com.fl.mfs.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class NoticeService {
	public static ConnectionFactory factory = new ConnectionFactory();
	public static Connection conn;
	public static Channel channel;

	public static void init() {

		try {
			factory.setUri("amqp://fuling:fuling@localhost:5672");
			factory.setAutomaticRecoveryEnabled(true);
			conn = factory.newConnection();
			channel = conn.createChannel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendMessage(String key, String message) {
		if (message == null)
			return;
		if (conn == null || channel == null)
			init();
		System.out.println("send:key:"+key+"title:"+message);
		byte[] messageBodyBytes = message.getBytes();
		try {
			channel.basicPublish("db.mfs", key, null, messageBodyBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getMessage(String name, String topic) throws IOException {
		if (conn == null || channel == null)
			init();
		channel.queueDeclare(name, true, false, false, null);
		channel.queueBind(name, "db.mfs", topic);
		long count = channel.consumerCount(name);
		if(count!=0)return;
		channel.basicConsume(name, false, name, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				
				long deliveryTag = envelope.getDeliveryTag();
				System.out.print(name+" receive:"+new String(body));
				channel.basicAck(deliveryTag, false);
			}
		});
	}
}
