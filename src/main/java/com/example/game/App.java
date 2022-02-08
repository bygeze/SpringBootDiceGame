package com.example.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.example.game.security.JWTAuthorizationFilter;
import com.mongodb.ServerAddress;

/* 
 * @SpringBootApplication covers: 
 * 		@EnableAutoConfiguration
 * 		@ComponentScan (in the package where this class is located)
 * 		@Configuration (allows to register extra beans in the context or import additional config classes) 
 */
//@SpringBootApplication

/*
 * @ComponentScan configured to search the rest of the packages of this app
 */

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,}
        )
@ComponentScan(basePackages = "com.example.game.repositories.mongo")
@ComponentScan(basePackages = "com.example.game.controller")

@EnableMongoRepositories
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	/*
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.GET, "/test").permitAll()
				.anyRequest().authenticated();
		}
	}*/
}
