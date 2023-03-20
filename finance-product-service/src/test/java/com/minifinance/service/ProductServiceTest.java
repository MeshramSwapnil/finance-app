package com.minifinance.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.minifinance.model.Products;
import com.minifinance.repository.ProductsRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	ProductService service;
	
	@Mock
	ProductsRepository repo;
	
	
	@Test
	public void getProducts() {
		Products p = new Products();
		p.setId(1L);
		p.setName("name");
		p.setDescription("description");
		p.setCharge(100);
		when(repo.findAll()).thenReturn(List.of(p));
		List<Products> products = service.products();
		Assertions.assertNotNull(products);
		Assertions.assertEquals(products.size(), 1);
		Assertions.assertEquals(products.get(0), p);
		
	}
	
	
}
