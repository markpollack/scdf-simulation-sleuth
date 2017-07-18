package hello;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class EmailMessageHandler {

	public EmailMessageHandler() {

	}

	@StreamListener(Sink.INPUT)
	public void handleMessage(Email email) {
		try {
			Thread.sleep(20);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Tap Received <" + email + ">");
	}
}
