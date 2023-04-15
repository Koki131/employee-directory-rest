package com.employeedirectory.rest.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		
		return new JdbcUserDetailsManager(dataSource);
	
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(configurer ->
					configurer.requestMatchers(HttpMethod.GET, "/api/v1/employees").hasRole("EMPLOYEE")
					.requestMatchers(HttpMethod.GET, "/api/v1/employees/**").hasRole("EMPLOYEE")
					.requestMatchers(HttpMethod.POST, "/api/v1/employees/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.PUT, "/api/v1/employees/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.DELETE, "/api/v1/employees/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.GET, "/api/v1/prospects/**").hasRole("EMPLOYEE")
					.requestMatchers(HttpMethod.POST, "/api/v1/prospects/contact/**").hasRole("EMPLOYEE")
					.requestMatchers(HttpMethod.POST, "/api/v1/prospects/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.PUT, "/api/v1/prospects/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.DELETE, "/api/v1/prospects/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.GET, "/api/v1/sales/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.POST, "/api/v1/sales/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.PUT, "/api/v1/sales/**").hasRole("MANAGER")
					.requestMatchers(HttpMethod.DELETE, "/api/v1/sales/**").hasRole("ADMIN")
				
		);
		
		http.httpBasic();
		
		http.csrf().disable();
		
		return http.build();
		
	}
	
}
