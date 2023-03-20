package com.minifinance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
public class TermsAndConditionTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser(username = "Swapnil")
	public void authorisedGET() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/termsandcondition/1000")).andDo(print()).andExpect(status().isOk()).andReturn();
		Assertions.assertEquals(MediaType.APPLICATION_OCTET_STREAM_VALUE, mvcResult.getResponse().getContentType());
	}

	@Test
	public void unauthorisedGET() throws Exception {
		this.mockMvc.perform(get("/termsandcondition/1000")).andDo(print()).andExpect(status().isUnauthorized()).andReturn();
	}
}
