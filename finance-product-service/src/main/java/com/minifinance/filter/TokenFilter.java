package com.minifinance.filter;

import java.io.IOException;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minifinance.service.CustomUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {

	public static final String BEARER = "Bearer";

	@Autowired
	ObjectMapper mapper;

	@Autowired
	CustomUserDetailService service;

	@Autowired
	JwtUtil jwtUtil;

	AntPathMatcher matcher = new AntPathMatcher();

	private static final String[] exclusions = new String[] { "/refreshToken" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwtToken = null;
			String username = null;
			String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(BEARER)) {
				jwtToken = bearerToken.substring(7, bearerToken.length());
				username = jwtUtil.extractUsername(jwtToken);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = service.loadUserByUsername(username);
					if (jwtUtil.validateToken(jwtToken, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());	
			ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Token Expired");
			mapper.writeValue(response.getWriter(), p);

		} 

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return Stream.of(exclusions).anyMatch(ex -> matcher.match(ex, request.getRequestURI()));
	}

}
