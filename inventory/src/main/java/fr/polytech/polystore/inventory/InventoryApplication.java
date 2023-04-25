package fr.polytech.polystore.inventory;

import fr.polytech.polystore.inventory.controllers.InventoryController;
import org.springframework.beans.factory.annotation.Autowired;
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
@Import(fr.polytech.polystore.common.configurations.OrderInventoryQueues.class)
public class InventoryApplication {

	@Autowired
	InventoryController inventoryController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(InventoryApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}
}
