package sv.edu.udb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringFacturacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFacturacionApplication.class, args);
		System.out.println(" Aplicación corriendo en: http://localhost:8081");
		System.out.println(" Consola H2: http://localhost:8081/h2-console");
		System.out.println(" Clientes API: http://localhost:8081/api/clients");
		System.out.println(" Facturas API: http://localhost:8081/api/invoices");
	}

	// Forzar el puerto 8081 desde código
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setPort(8081);
	}
}
