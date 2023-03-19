package com.minifinance.security.model;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationConfig {

	@NotNull
	@NotEmpty
	String imagePath;

	@NotNull
	@NotEmpty
	String accessTokenSecret;

	@NotNull
	@NotEmpty
	Duration accessTokenValidity;

	@NotNull
	@NotEmpty
	String refreshTokenSecret;

	@NotNull
	@NotEmpty
	String refreshTokenSalt;

}
