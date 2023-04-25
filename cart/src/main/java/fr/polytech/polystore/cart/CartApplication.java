package fr.polytech.polystore.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({@org.springframework.context.annotation.PropertySource("classpath:application.properties"), @org.springframework.context.annotation.PropertySource("classpath:common.properties")})
@Import({fr.polytech.polystore.common.configurations.OrderCartQueues.class, fr.polytech.polystore.common.configurations.RabbitMQConfiguration.class})
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CartApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
