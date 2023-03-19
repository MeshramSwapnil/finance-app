package com.minifinance.security.handlers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minifinance.constants.SecurityConstants;
import com.minifinance.filter.JwtUtil;
import com.minifinance.model.AuthResponse;
import com.minifinance.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	TextEncryptor textEncryptor;

	@Autowired
	UserRepository repo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String refreshToken = textEncryptor.encrypt(user.getUsername());
		int res = repo.updateRefreshToken(refreshToken, user.getUsername());
		log.info("Updated Refresh Token For {} Status {} ", user.getUsername(), res);
		ResponseCookie cookie = ResponseCookie.from(SecurityConstants.X_REFRESH_TOKEN, refreshToken).secure(false)
				.httpOnly(true).path("/refreshToken").build();
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		response.setHeader(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user));
		response.setHeader(SecurityConstants.X_REFRESH_TOKEN, refreshToken);
		AuthResponse authResponse = new AuthResponse("Authentication Successful", true, null);
		mapper.writeValue(response.getWriter(), authResponse);
		log.info("Authentication success for  {} ", user.getUsername());
	}

}
