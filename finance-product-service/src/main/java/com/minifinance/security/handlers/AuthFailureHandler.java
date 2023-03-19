package com.minifinance.security.handlers;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minifinance.model.AuthResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	ObjectMapper mapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		AuthResponse authRes = new AuthResponse();
		authRes.setMessage(
				StringUtils.isEmpty(exception.getMessage()) ? "Authentication Failure" : exception.getMessage());
		authRes.setSuccess(false);
		authRes.setData(exception.getMessage());
		mapper.writeValue(response.getWriter(), authRes);
		log.error("Authentication failure {} ", exception.getMessage());

	}

}
