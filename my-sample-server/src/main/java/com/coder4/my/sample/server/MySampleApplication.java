package com.coder4.my.sample.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySampleApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(MySampleApplication.class);

		// To disabled web environment, change `true` to `false`
		application.setWebEnvironment(true);
		application.run(args);
	}
}
