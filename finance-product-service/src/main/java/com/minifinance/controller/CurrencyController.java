package com.minifinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minifinance.constants.Constants;
import com.minifinance.model.ApiResponse;
import com.minifinance.repository.CurrencyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/currency")
public class CurrencyController {


	@Autowired
	CurrencyRepository repo;
	
	@GetMapping
	public ApiResponse getAll(){
		return ApiResponse.builder().data(repo.findAll()).message(Constants.SUCCESS).build();
	}
	
	
}
