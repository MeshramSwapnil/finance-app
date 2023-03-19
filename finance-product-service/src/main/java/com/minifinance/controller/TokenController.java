package com.minifinance.controller;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minifinance.constants.SecurityConstants;
import com.minifinance.filter.JwtUtil;
import com.minifinance.model.AuthResponse;
import com.minifinance.model.Users;
import com.minifinance.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TokenController {

	@Autowired
	TextEncryptor textEncryptor;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserRepository repo;

	@GetMapping("/refreshToken")
	public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		Cookie xRefreshToken = null;
		String token = null;
		if(Objects.nonNull(cookies)) {
			xRefreshToken = Arrays.stream(cookies)
					.filter(cookie -> SecurityConstants.X_REFRESH_TOKEN.equals(cookie.getName())).findFirst().orElse(null);
			if (ObjectUtils.isEmpty(xRefreshToken)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Unauthorised", false, null));
			}
			token = xRefreshToken.getValue();
		} else {
			token = req.getHeader(SecurityConstants.X_REFRESH_TOKEN);
		}
		if(StringUtils.isEmpty(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Token Expired", false, null));
		}
		String user = textEncryptor.decrypt(token);
		Users foundUser = repo.findById(user).orElse(null);
		if (ObjectUtils.isEmpty(foundUser) || StringUtils.isEmpty(foundUser.getRefreshToken())
				|| !token.equals(foundUser.getRefreshToken())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Unauthorized", false, null));
		}
		log.info("Token Found For {} ", user);
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION,
						jwtUtil.generateToken(User.builder().username(foundUser.getUsername())
								.password(foundUser.getPassword()).roles(foundUser.getRole().toUpperCase()).build()))
				.body(new AuthResponse("Sucess", true, null));
	}
}
