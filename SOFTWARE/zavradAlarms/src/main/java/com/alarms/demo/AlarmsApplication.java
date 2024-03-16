package com.alarms.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.api.objects.Chat;

@SpringBootApplication 
public class AlarmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlarmsApplication.class, args);
		
}
}
