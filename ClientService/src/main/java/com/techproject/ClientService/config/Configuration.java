package com.techproject.ClientService.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Value("${BackEndService.base.url}")
	private String BEServiceBaseUrl;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl(BEServiceBaseUrl).build();
	}

}
