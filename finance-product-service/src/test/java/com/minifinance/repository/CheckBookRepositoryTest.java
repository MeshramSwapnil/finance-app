package com.minifinance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.minifinance.model.CheckBook;

@DataJpaTest
public class CheckBookRepositoryTest {

	@Autowired
	CheckBookRepository repository;

	@Test
	public void finalltest() {
		List<CheckBook> findAll = repository.findAll();
		assertThat(findAll).isNotEmpty();
		assertThat(findAll).hasSizeBetween(0, 13);
	}

}
