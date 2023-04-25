package fr.polytech.polystore.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @org.springframework.context.annotation.PropertySource("classpath:application.properties"),
        @org.springframework.context.annotation.PropertySource("classpath:common.properties")
})
@Import({
    fr.polytech.polystore.common.configurations.OrderInventoryQueues.class,
    fr.polytech.polystore.common.configurations.OrderCartQueues.class,
})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OrderApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
