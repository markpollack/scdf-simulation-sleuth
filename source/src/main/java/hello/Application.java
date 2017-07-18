
package hello;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.GlobalChannelInterceptor;

@SpringBootApplication
public class Application {

	@Bean
	@GlobalChannelInterceptor(patterns = "${spring.sleuth.integration.patterns:*}", order = 10)
	public ProvenanceInterceptor provenanceInterceptor(Tracer tracer) {
		return new ProvenanceInterceptor(tracer);
	}

	public static void main(String[] args) {
		// Launch the application
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		EmailSendingBean emailSendingBean = context.getBean(EmailSendingBean.class);
		System.out.println("Sending an email message.");
		emailSendingBean.sendEmail(new Email("info@example.com", "Hello"));

	}

}
