package com.cvatos.chorifybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "com.cvatos.chorifybackend.model")
public class ChorifyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChorifyBackendApplication.class, args);
	}

}
