package hello;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Processor.class)
public class EmailMessageHandler {


	public EmailMessageHandler() {
	}

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Email handleMessage(Email email) {
		System.out.println("Received <" + email + ">");
		return new Email("info@example.com", "Hello-transformed");
	}
}
