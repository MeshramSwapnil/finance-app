package com.minifinance.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/termsandcondition")
public class TermsAndConditionController {

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getTermsAndCondition(@NotNull @PathVariable Integer id) throws IOException{
		File file = ResourceUtils.getFile("classpath:sample-terms-conditions-agreement.pdf");
		return ResponseEntity.ok(FileCopyUtils.copyToByteArray(file));
		
	}
	
}
