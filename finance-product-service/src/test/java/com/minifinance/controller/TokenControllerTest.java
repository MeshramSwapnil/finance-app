package com.minifinance.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.minifinance.filter.JwtUtil;
import com.minifinance.model.Users;
import com.minifinance.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
public class TokenControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserRepository repo;
	
	@MockBean
	TextEncryptor textEncryptor;
	
	@MockBean
	JwtUtil jwtUtil;
	
	@Test
	@WithMockUser(username = "Swapnil")
	public void withXRefreshToken() throws Exception {
		
		when(repo.findById(Mockito.anyString())).thenReturn(Optional.of(new Users("Swapnil","password","USER","token")));	
		when(textEncryptor.decrypt("token")).thenReturn("token");
		when(jwtUtil.generateToken(Mockito.any())).thenReturn("token");
		MvcResult mvcResult = this.mockMvc.perform(get("/refreshToken").header("x-refresh-token", "token"))
				
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true)).andReturn();
		Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
	}

	@Test
	public void withXRefreshTokenNoUserFound() throws Exception {
		when(repo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));	
		when(textEncryptor.decrypt("token")).thenReturn("token");
		when(jwtUtil.generateToken(Mockito.any())).thenReturn("token");
		this.mockMvc.perform(get("/refreshToken").header("x-refresh-token", "token"))
				.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.success").value(false)).andReturn();
	}
	
	@Test
	public void withoutXRefreshToken() throws Exception {
		this.mockMvc.perform(get("/refreshToken")).andExpect(status().isUnauthorized()).andReturn();
	}
	
	
}
