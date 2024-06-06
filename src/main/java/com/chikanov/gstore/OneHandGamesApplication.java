package com.chikanov.gstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OneHandGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneHandGamesApplication.class, args);
	}
}
