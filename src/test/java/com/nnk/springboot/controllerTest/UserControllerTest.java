package com.nnk.springboot.controllerTest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	User user;
	Integer id;
	
	@BeforeEach
	public void setUp() {
		user = new User("userName", "1Password!", "fullname", "USER");
		user = userRepository.save(user);
		id = user.getUserId();				
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void addUserFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("user/add"));
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void validateUserTest() throws Exception {	
		int resultBefore = userRepository.findAll().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/user/validate").with(csrf())
				.param("username", "usernameTest")
				.param("password", "passwordTest8!")
				.param("fullname", "fullnameTest")
				.param("role", "USER"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/user/list")).andDo(print());
		
		int resultAfter = userRepository.findAll().size();
		assertTrue(resultAfter == resultBefore + 1);			
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void validateUserTest_NotValid() throws Exception {	
		int resultBefore = userRepository.findAll().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/user/validate").with(csrf())
				.param("username", "")
				.param("password", "")
				.param("role", "ZZZ"))
		.andExpect(MockMvcResultMatchers.view().name("user/add"));
		
		int resultAfter = userRepository.findAll().size();
		assertTrue(resultAfter == resultBefore);			
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void showUpdateFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/update/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
		.andExpect(MockMvcResultMatchers.view().name("user/update"));
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void updateUserTest() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.post("/user/update/"+id).with(csrf())
				.param("userId", id.toString())
				.param("username", "newUsername")
				.param("password", "newPassword1!")
				.param("fullname", "newFullname")
				.param("role", "USER"))
		.andExpect(MockMvcResultMatchers.view().name("redirect:/user/list"));
		User result = userRepository.findById(id).orElse(null);
		assertTrue(result.getUsername().equals("newUsername"));
		
	}
	
	@Test
	@WithMockUser (roles = {"ADMIN"})
	public void deleteUserTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/"+id))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
		
		assertNull(userRepository.findById(id).orElse(null));
	}
	
	
	

}
