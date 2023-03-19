package com.minifinance.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minifinance.constants.SecurityConstants;
import com.minifinance.filter.TokenFilter;
import com.minifinance.security.handlers.AuthFailureHandler;
import com.minifinance.security.handlers.AuthSuccessHandler;
import com.minifinance.security.handlers.LogOutSucessHandler;
import com.minifinance.security.model.ApplicationConfig;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration implements WebMvcConfigurer {

	@Autowired
	ApplicationConfig config;

	@Autowired
	AuthSuccessHandler successHandler;

	@Autowired
	AuthFailureHandler failureHandler;

	@Autowired
	LogOutSucessHandler logoutSuccessHandler;

	@Autowired
	TokenFilter tokenFilter;

	@Autowired
	ObjectMapper mapper;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations(ResourceUtils.FILE_URL_PREFIX.concat(config.getImagePath()));
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers(PathRequest.toH2Console()).permitAll()
				.requestMatchers("/refreshToken").permitAll()
				.anyRequest().authenticated().and()
				.formLogin().successHandler(successHandler).failureHandler(failureHandler)
				.and().logout().clearAuthentication(true).logoutSuccessHandler(logoutSuccessHandler).deleteCookies(SecurityConstants.X_REFRESH_TOKEN)
				.and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.and().csrf().disable()
				.headers().frameOptions().sameOrigin()
				.and().addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(tokenFilter, LogoutFilter.class).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and().cors()
				.and().build();
	}
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // todo properties by environment
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","HEAD","OPTIONS"));
		configuration.setExposedHeaders(Arrays.asList(SecurityConstants.AUTHORIZATION, SecurityConstants.X_REFRESH_TOKEN));
		configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	private AuthenticationEntryPoint authenticationEntryPoint = (request, response, ex) -> {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
		mapper.writeValue(response.getWriter(), p);
		
	};

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
