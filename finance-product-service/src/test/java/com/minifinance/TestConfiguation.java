package com.minifinance;

import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguation {

	@MockBean
	H2ConsoleProperties pros;
	
}
