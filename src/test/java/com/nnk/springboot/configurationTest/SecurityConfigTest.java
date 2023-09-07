package com.nnk.springboot.configurationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Test
	@WithMockUser
	public void filterChainTestWithLoggedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void filterChainTestWithoutLoggin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser
	public void filterChainTestForAdminOnly_Failure() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void filterChainTestForAdminOnly_Succes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
