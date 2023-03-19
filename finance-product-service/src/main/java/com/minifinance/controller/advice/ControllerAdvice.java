package com.minifinance.controller.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex, WebRequest request) {
		ProblemDetail p = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		p.setProperty("error", details);
		log.error("Exception {} ", ex.getMessage());
		return new ResponseEntity<>(p, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
