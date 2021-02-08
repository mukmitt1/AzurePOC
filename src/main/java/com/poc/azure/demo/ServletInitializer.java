package com.poc.azure.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import service.MessagingController;

public class ServletInitializer extends SpringBootServletInitializer {

	private static final Logger LOGGER= LoggerFactory.getLogger(ServletInitializer.class);


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		LOGGER.info("In the ServletInitlizer.configure() method");

		return application.sources(AzurePocApplication.class);
	}

}
