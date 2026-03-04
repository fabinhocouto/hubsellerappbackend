package com.br.hubsellerappbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	 public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**") // Permite CORS para todos os endpoints
		 			.allowedOriginPatterns("*")
	                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // Métodos permitidos
	                .allowedHeaders("*") // Headers permitidos
	                .allowCredentials(true); // Permite credenciais (cookies, tokens)
	 }
}
