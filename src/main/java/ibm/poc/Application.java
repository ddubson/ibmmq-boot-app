package ibm.poc;

import com.ibm.mq.jms.MQQueue;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.jms.JMSException;

@SpringBootApplication
@ImportResource("integration-context.xml")
public class Application implements ApplicationRunner{
    private final MessageGateway messageGateway;

    public Application(MessageGateway messageGateway) {
        this.messageGateway = messageGateway;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Send one XML message to queue");
        messageGateway.send(MessageBuilder.withPayload("<?xml version=\"1.0\" encoding=\"UTF-8\"?><data><entity>1</entity></data>").build());
    }

    @Bean("devQueue1")
    public MQQueue devQueue1() throws JMSException {
        return new MQQueue("DEV.QUEUE.1");
    }

    @Bean
    @ServiceActivator(inputChannel = "inboundChannel")
    public XMLPrinter xmlPrinter() {
        return new XMLPrinter();
    }

    @Bean("inboundChannel")
    public DirectChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean("outboundChannel")
    public DirectChannel outboundChannel() {
        return new DirectChannel();
    }
}
