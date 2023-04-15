package com.employeedirectory.rest.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class Config implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		Path imageUploadDir = Paths.get("./employee-images/");
		
		String imageUploadPath = imageUploadDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/employee-images/**").addResourceLocations("file:/" + imageUploadPath + "/");
	}

	
}

	

