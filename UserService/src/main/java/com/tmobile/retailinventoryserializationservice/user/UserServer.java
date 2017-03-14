
package com.tmobile.retailinventoryserializationservice.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "user-server");
		SpringApplication.run(UserServer.class, args);
	}
}
