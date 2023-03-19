package com.minifinance.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import com.minifinance.security.model.ApplicationConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
public class MiniFinanceConfiguration {

	@Autowired
	ApplicationConfig config;

	@Bean
	TextEncryptor textEncryptor() {
		return Encryptors.text(config.getRefreshTokenSecret(), config.getRefreshTokenSalt());
	}
}
