package com.nnk.springboot.controllerTest;

import static org.junit.Assert.assertEquals;
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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BidListControllerTest {

	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private BidListService bidListService;
	
	BidList bidList;
	Integer id;
	
	@BeforeEach
	public void setUp() {
		bidList = new BidList("accountTest", "typeTest", 50d);
		bidList = bidListService.addBidList(bidList);
		id = bidList.getBidListId();
		
		
	}
	
	@Test
	@WithMockUser
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser
	public void addBidFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("bidList/add"));
	}
	
	@Test
	@WithMockUser
	public void validateBidTest() throws Exception {	
		int resultBefore = bidListService.getAllBidLists().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate").with(csrf())
				.param("account", "accountTest")
				.param("type", "typeTest")
				.param("bidQuantity", bidList.getBidQuantity().toString()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list")).andDo(print());
		
		int resultAfter = bidListService.getAllBidLists().size();
		assertTrue(resultAfter == resultBefore + 1);
			
	}
	
	@Test
	@WithMockUser
	public void showUpdateFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("bidList"))
		.andExpect(MockMvcResultMatchers.view().name("bidList/update"));
	}
	
	@Test
	@WithMockUser
	public void updateBidTest() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/"+id).with(csrf())
				.param("bidListId", id.toString())
				.param("account", "newAccountTest"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list")).andDo(print());
		BidList result = bidListService.getBidListById(id);
		assertEquals("newAccountTest", result.getAccount());
		
	}
	
	@Test
	@WithMockUser
	public void deleteBidTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/"+id))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
		
		assertNull(bidListService.getBidListById(id));
	}

}
