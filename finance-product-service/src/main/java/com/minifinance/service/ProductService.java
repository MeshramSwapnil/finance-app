package com.minifinance.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.minifinance.constants.Constants;
import com.minifinance.model.Products;
import com.minifinance.repository.ProductsRepository;
import com.minifinance.security.model.ApplicationConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	@Autowired
	ProductsRepository repo;
	
	@Autowired
	ApplicationConfig config;
	
	public List<Products> products(){
		List<Products> findAll = repo.findAll();
		findAll.forEach(val -> val.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString().concat(Constants.URL_SEPARATOR).concat(val.getImageUrl())));
		return findAll;
	}
	
	public Products addProduct(Products product, MultipartFile file) {
		try {
			Path dest = Paths.get(config.getImagePath().concat(File.separator).concat(file.getOriginalFilename()));
			file.transferTo(dest);
			product.setImageUrl(config.getImagePath().concat(Constants.URL_SEPARATOR).concat(file.getOriginalFilename()));
		} catch (IllegalStateException | IOException e) {
			log.error("Error in copying File {} ", e.getMessage());
		}
		return repo.save(product);
	}
	
	
	public void removeProduct(Integer p) {
		Optional<Products> foundProduct = repo.findById(p);
		if(foundProduct.isPresent()) {
			try {
				Files.delete(Paths.get(foundProduct.get().getImageUrl()));
			} catch (IOException e) {
				log.error("Error in deleting File {} ", e.getMessage());
			}
		}
		repo.deleteById(p);
	}
}
