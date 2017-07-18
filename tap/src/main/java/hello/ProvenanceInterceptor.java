package hello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * @author Mark Pollack
 */
public class ProvenanceInterceptor extends ChannelInterceptorAdapter {

	private static final Log log = LogFactory.getLog(ProvenanceInterceptor.class);

	private Tracer tracer;

	public ProvenanceInterceptor(Tracer tracer) {
		this.tracer = tracer;
	}

	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		Span parentSpan = tracer.getCurrentSpan();
		//TODO how to tell if it is being sent again due to retry....
		log.info("Adding a payload tag....");
		parentSpan.tag("payload", message.getPayload().toString());
		return message;

	}
}
