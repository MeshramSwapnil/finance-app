package com.minifinance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.minifinance.model.Currency;

@DataJpaTest
public class CurrencyRepositoryTest {

	@Autowired
	CurrencyRepository repository;

	@Test
	public void finalltest() {
		List<Currency> findAll = repository.findAll();
		assertThat(findAll).isNotEmpty();
		assertThat(findAll).hasSizeBetween(0, 13);
	}
	
	

}
