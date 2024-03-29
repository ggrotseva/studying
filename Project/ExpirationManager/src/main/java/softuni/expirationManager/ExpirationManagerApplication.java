package softuni.expirationManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExpirationManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpirationManagerApplication.class, args);
	}

}
