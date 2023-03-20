package com.minifinance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.minifinance.model.Products;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	ProductsRepository repository;

	@Test
	public void finalltest() {
		List<Products> findAll = repository.findAll();
		assertThat(findAll).isNotEmpty();
		assertThat(findAll).hasSizeBetween(0, 13);
	}
	
	@Test
	public void findById() {
		Products p = getTestProduct();
		p.setId(null);
		Integer id = entityManager.merge(p).getId();
		Optional<Products> findById = repository.findById(id);
		assertThat(findById).isNotEmpty();
		assertThat(findById.get().getId()).isEqualTo(id);
		
	}
	
	@Test
	public void delete() {
		Products p = getTestProduct();
		p.setId(null);
		Integer id = entityManager.merge(p).getId();
		repository.deleteById(id);
		Optional<Products> findById = repository.findById(id);
		assertThat(findById).isEmpty();
		
	}
	
	private Products getTestProduct() {
		Products p = new Products();
		p.setId(1);
		p.setName("name");
		p.setDescription("description");
		p.setCharge(100);
		return p;
	}

}
