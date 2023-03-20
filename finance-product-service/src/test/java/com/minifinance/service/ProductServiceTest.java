package com.minifinance.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.minifinance.model.Products;
import com.minifinance.repository.ProductsRepository;
import com.minifinance.security.model.ApplicationConfig;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	ProductService service;
	
	@Mock
	ProductsRepository repo;
	
	@Mock
	ApplicationConfig config;
	
	
	@Test
	public void getProducts() {
		Products p = getTestProduct();
		when(repo.findAll()).thenReturn(List.of(p));
		List<Products> products = service.products();
		Assertions.assertNotNull(products);
		Assertions.assertEquals(products.size(), 1);
		Assertions.assertEquals(products.get(0), p);
	}
	
	@Test
	public void addProducts() {
		Products p = getTestProduct();
		
		MultipartFile file = mock(MultipartFile.class);
		when(config.getImagePath()).thenReturn("images");
		when(file.getOriginalFilename()).thenReturn("file.jpg");
		when(repo.save(p)).thenReturn(p);
		Products products = service.addProduct(p, file);
		Assertions.assertNotNull(products);
		Assertions.assertEquals(products.getId(), 1);
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
