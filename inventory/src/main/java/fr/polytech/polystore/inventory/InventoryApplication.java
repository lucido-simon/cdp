package fr.polytech.polystore.inventory;

import fr.polytech.polystore.inventory.controllers.InventoryController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class InventoryApplication {

	@Autowired
	InventoryController inventoryController;
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(InventoryApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}
}
