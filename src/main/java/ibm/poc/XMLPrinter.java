package ibm.poc;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class XMLPrinter {
    public Message<?> print(Message<?> message) {
        System.out.println("Consumed message off of IBM MQ queue.\n");
        System.out.println("----------------------------------------------------------------");
        message.getHeaders().forEach((k,v) -> System.out.println("Header: " + k + ": " + v));
        System.out.println("Payload: "+ message.getPayload().toString());
        System.out.println("----------------------------------------------------------------");
        System.out.println("\n...Sending to RabbitMQ via AMQP.\n");

        // Send the message on to the predetermined output-channel
        return message;
    }
}
