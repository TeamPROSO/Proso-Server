package com.prosoteam.proso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProsoApplication.class, args);
	}

}
