package com.worldfirst.featuretoggle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
		basePackageClasses = {FeatureToggleApplication.class, Jsr310JpaConverters.class} // tells Spring to register converters
)
@SpringBootApplication
public class FeatureToggleApplication {

	/**
	 * Configuration for Jackson Databind Serializer
	 *
	 * @return ObjectMapper
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		// Proper handling for LocalDateTime type
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// use snake_case when de/serializing
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

		return objectMapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(FeatureToggleApplication.class, args);
	}
}