package com.example.chatTest;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatTestApplication.class, args);
	}

}
