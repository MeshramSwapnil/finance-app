package com.minifinance.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.minifinance.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Initializer implements ApplicationRunner {

	@Autowired
	UserRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		User u = new User("Swapnil", "Password", "admin", null);
//		repository.save(u);

	}

}
