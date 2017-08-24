package ibm.poc;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class XMLPrinter {
    public void print(Message<?> message) {
        System.out.println("Consumed message off of queue.\n");
        System.out.println("----------------------------------------------------------------");
        message.getHeaders().forEach((k,v) -> System.out.println("Header: " + k + ": " + v));
        System.out.println("Payload: "+ message.getPayload().toString());
        System.out.println("----------------------------------------------------------------");
        System.out.println("DONE.\n");
    }
}
