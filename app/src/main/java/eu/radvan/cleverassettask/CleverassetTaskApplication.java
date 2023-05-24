package eu.radvan.cleverassettask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CleverassetTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleverassetTaskApplication.class, args);
	}

}
