package ibm.poc;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class AMQPConsumer {
    public void consume(Message<?> message) {
        System.out.println("Consuming message from RabbitMQ via AMQP.");
        System.out.println("----------------------------------------------------------------");
        //message.getHeaders().forEach((k,v) -> System.out.println("Header: " + k + ": " + v));
        System.out.println("Payload: "+ message.getPayload().toString());
        System.out.println("----------------------------------------------------------------");
        System.out.println("DONE!");
    }
}
