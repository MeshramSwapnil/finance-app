package com.minifinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minifinance.constants.Constants;
import com.minifinance.model.ApiResponse;
import com.minifinance.model.Products;
import com.minifinance.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	ProductService productService;

	
	@GetMapping
	public ApiResponse products(){
		return ApiResponse.builder().data(productService.products()).message(Constants.SUCCESS).build();
	}
	
	@PostMapping
	@Secured("ROLE_ADMIN")
	public ApiResponse addProduct(@RequestPart("data") @Valid Products product, @RequestPart("file") MultipartFile file){
		Products addProduct = productService.addProduct(product, file);
		return ApiResponse.builder().data(addProduct).message(Constants.SUCCESS).build();
	}
	
	@DeleteMapping
	@Secured("ROLE_ADMIN")
	public ApiResponse removeProduct(@RequestBody @NotNull Integer product){
		productService.removeProduct(product);
		return ApiResponse.builder().data(product).message(Constants.SUCCESS).build();
	}
}
