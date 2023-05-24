package eu.radvan.cleverassettask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IntersectionAppRunner {

	public static void main(String[] args) {
		SpringApplication.run(IntersectionAppRunner.class, args);
	}

}
