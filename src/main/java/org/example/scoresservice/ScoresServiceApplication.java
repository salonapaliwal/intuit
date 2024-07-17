package org.example.scoresservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.scoresservice")
public class ScoresServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoresServiceApplication.class, args);
	}

}
