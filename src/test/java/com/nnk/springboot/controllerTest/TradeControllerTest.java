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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TradeControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private TradeService tradeService;
	
	Trade tradeTest;
	Integer id;
	
	@BeforeEach
	public void setUp() {
		tradeTest = new Trade("accountTest", "typeTest");
		tradeTest = tradeService.addTrade(tradeTest);
		id = tradeTest.getTradeId();		
		
	}
	
	@Test
	@WithMockUser
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser
	public void addTradeFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("trade/add"));
	}
	
	@Test
	@WithMockUser
	public void validateTradeTest() throws Exception {	
		int resultBefore = tradeService.getAllTrades().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate").with(csrf())
				.param("account", "otherTestAcount")
				.param("type", "otherTestType"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list")).andDo(print());
		
		int resultAfter = tradeService.getAllTrades().size();
		assertTrue(resultAfter == resultBefore + 1);
			
	}
	
	@Test
	@WithMockUser
	public void showUpdateFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("trade"))
		.andExpect(MockMvcResultMatchers.view().name("trade/update"));
	}
	
	@Test
	@WithMockUser
	public void updateTradeTest() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/"+id).with(csrf())
				.param("tradeId", id.toString())
				.param("account", "modifierAccount"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list")).andDo(print());
		Trade result = tradeService.getTradeById(id);
		assertTrue(result.getAccount().equals("modifierAccount"));
		
	}
	
	@Test
	@WithMockUser
	public void deleteTradeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/"+id))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
		
		assertNull(tradeService.getTradeById(id));
	}

}
