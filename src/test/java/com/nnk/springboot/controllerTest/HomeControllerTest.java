package com.nnk.springboot.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HomeControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Test
	@WithMockUser
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void adminHomeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/home"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
	}

}
