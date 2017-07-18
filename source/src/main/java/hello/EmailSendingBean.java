package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class EmailSendingBean {

	private Source source;

	@Autowired
	public EmailSendingBean(Source source) {
		this.source = source;
	}

	public void sendEmail(Email email) {
		source.output().send(MessageBuilder.withPayload(email).build());
	}
}
