package com.minifinance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minifinance.model.Products;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
public class ProductsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@WithMockUser(username = "Swapnil")
	public void authorisedGET() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("SUCCESS")).andReturn();
		Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
	}

	@Test
	public void unauthorisedGET() throws Exception {
		this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isUnauthorized()).andReturn();
	}

	@Test
	@WithMockUser(username = "Swapnil", roles = "ADMIN")
	public void authorisedADMINPOST() throws Exception {

		Products products = new Products();
		products.setName("Debit Card");
		products.setDescription("Debit Card");
		products.setCharge(20);
		MockMultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some other type".getBytes());
		MockMultipartFile data = new MockMultipartFile("data", "", "application/json",
				mapper.writeValueAsString(products).getBytes());
		MvcResult mvcResult = this.mockMvc.perform(multipart("/products").file(file).file(data)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("SUCCESS")).andReturn();
		Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
	}

	@Test
	@WithMockUser(username = "Swapnil", roles = "USER")
	public void authorisedUSERPOST() throws Exception {

		Products products = new Products();
		products.setName("Debit Card");
		products.setDescription("Debit Card");
		products.setCharge(20);
		MockMultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some other type".getBytes());
		MockMultipartFile data = new MockMultipartFile("data", "", "application/json",
				mapper.writeValueAsString(products).getBytes());
		this.mockMvc.perform(multipart("/products").file(file).file(data)).andDo(print())
				.andExpect(status().is5xxServerError()).andReturn();
	}

	@Test
	public void unauthorisedPOST() throws Exception {
		this.mockMvc.perform(post("/products")).andDo(print()).andExpect(status().isUnauthorized()).andReturn();
	}

	@Test
	@WithMockUser(username = "Swapnil", roles = "ADMIN")
	public void authorisedADMINDELETE() throws Exception {

		MvcResult mvcResult = this.mockMvc.perform(delete("/products").content(mapper.writeValueAsString(1000)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.message").value("SUCCESS"))
				.andReturn();
		Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
	}

	@Test
	@WithMockUser(username = "Swapnil", roles = "USER")
	public void authorisedUSERDELETE() throws Exception {
		this.mockMvc.perform(delete("/products").content(mapper.writeValueAsString(1000)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is5xxServerError()).andReturn();
	}

	@Test
	public void unauthorisedDELETE() throws Exception {
		this.mockMvc.perform(delete("/products").content(mapper.writeValueAsString(1000)).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnauthorized()).andReturn();
	}
}
