package com.algaworks.algafoodapi2;

import com.algaworks.algafoodapi2.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class AlgafoodApi2Application {

	public static void main(String[] args) {

        SpringApplication.run(AlgafoodApi2Application.class, args);
	}





}
