package com.devsuperior.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {
	
		  // O Bean indica que a instância new BCryptPasswordEncoder() é um componente gerenciado pelo Spring Boot.
	@Bean // Bean é para injetar método. Componente do spring: gerencia a injeção de dependência em outros componentes.
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
