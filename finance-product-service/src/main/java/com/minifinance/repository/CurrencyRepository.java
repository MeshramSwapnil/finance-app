package com.minifinance.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.minifinance.model.Currency;

public interface CurrencyRepository extends ListCrudRepository<Currency,String>{

}
