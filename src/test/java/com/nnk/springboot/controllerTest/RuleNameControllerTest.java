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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RuleNameControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private RuleNameService ruleNameService;
	
	RuleName ruleName;
	Integer id;
	
	@BeforeEach
	public void setUp() {
		ruleName = new RuleName();
		ruleName = ruleNameService.addRuleName(ruleName);
		id = ruleName.getRuleNameId();				
	}
	
	@Test
	@WithMockUser
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser
	public void addRuleNameFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("ruleName/add"));
	}
	
	@Test
	@WithMockUser
	public void validateRuleNameTest() throws Exception {	
		int resultBefore = ruleNameService.getAllRules().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate").with(csrf())
				.param("name", "newNameTest")
				.param("json", "newJson"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list")).andDo(print());
		
		int resultAfter = ruleNameService.getAllRules().size();
		assertTrue(resultAfter == resultBefore + 1);
			
	}
	
	@Test
	@WithMockUser
	public void showUpdateFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
		.andExpect(MockMvcResultMatchers.view().name("ruleName/update"));
	}
	
	@Test
	@WithMockUser
	public void updateRuleNameTest() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/"+id).with(csrf())
				.param("ruleNameId", id.toString())
				.param("name", "updatedNameTest")
				.param("json", "updatedJson"))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list")).andDo(print());
		RuleName result = ruleNameService.getRuleNameById(id);
		assertTrue(result.getName().equals("updatedNameTest"));
		
	}
	
	@Test
	@WithMockUser
	public void deleteRuleNameTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/"+id))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"));
		
		assertNull(ruleNameService.getRuleNameById(id));
	}

}
