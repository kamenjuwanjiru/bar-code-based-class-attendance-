package com.gatepass.GatePass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@SpringBootApplication
@ComponentScan(basePackages = "com.gatepass.GatePass.*")
@EntityScan(basePackages = "com.gatepass.GatePass.entities")

public class GatePassApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatePassApplication.class, args);
	}
		@Bean
		public PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
}
