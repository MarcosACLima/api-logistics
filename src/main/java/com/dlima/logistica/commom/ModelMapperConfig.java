package com.dlima.logistica.commom;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	// Sera gerenciado pelo Spring e disponibilizado para injecao em outras classes
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
