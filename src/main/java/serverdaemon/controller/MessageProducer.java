package serverdaemon.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import shared.model.user.Follower;
import shared.model.user.Following;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageProducer {
    private ConnectionFactory factory = new ConnectionFactory();
    private Connection conn;
    private Channel channel;
    private String exchangeName = "exchange";
    private String routingKey = "route";
    private String queueName = "queue";

    public MessageProducer() {
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
    }

    public void connect() {
        try {
            conn = factory.newConnection();
            channel = conn.createChannel();
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void send(Following following, Follower follower) {
        try {
            String message = follower.getUserInfo().getVkId() + " " + following.getUserInfo().getVkId();
            channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            channel.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
