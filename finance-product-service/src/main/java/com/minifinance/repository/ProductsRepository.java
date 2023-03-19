package com.minifinance.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.minifinance.model.Products;

public interface ProductsRepository extends ListCrudRepository<Products, Integer> {

}
