package fr.polytech.polystore.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({@org.springframework.context.annotation.PropertySource("classpath:application.properties"), @org.springframework.context.annotation.PropertySource("classpath:common.properties")})
@Import({ fr.polytech.polystore.common.configurations.OrderPaymentQueues.class, fr.polytech.polystore.common.configurations.RabbitMQConfiguration.class})
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

}
