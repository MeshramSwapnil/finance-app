package com.minifinance.security.handlers;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minifinance.filter.JwtUtil;
import com.minifinance.model.AuthResponse;
import com.minifinance.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogOutSucessHandler implements LogoutSuccessHandler {

	public static final String BEARER = "Bearer";

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserRepository repo;
	
	@Autowired
	ObjectMapper mapper;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isNoneEmpty(token) && StringUtils.startsWith(token, BEARER)) {
			String jwtToken = token.substring(7, token.length());
			String username = jwtUtil.extractUsername(jwtToken);
			int res = repo.updateRefreshToken(null, username);
			log.info("Logout success for {} status {} ", username, res);
			mapper.writeValue(response.getWriter(), new AuthResponse("LogoutSucess", true, null));
		}
	}

}
