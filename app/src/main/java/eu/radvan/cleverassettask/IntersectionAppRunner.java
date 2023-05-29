package eu.radvan.cleverassettask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
public class IntersectionAppRunner {

	public static void main(String[] args) {
		SpringApplication.run(IntersectionAppRunner.class, args);
	}

}
