package fr.polytech.polystore.catalog;

import fr.polytech.polystore.catalog.controllers.ProductController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CatalogApplication {

	@Autowired
	ProductController productController;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CatalogApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}
}
