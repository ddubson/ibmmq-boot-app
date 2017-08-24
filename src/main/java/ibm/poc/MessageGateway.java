package ibm.poc;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway
public interface MessageGateway {
    @Gateway(requestChannel = "outboundChannel")
    void send(Message<?> message);
}
