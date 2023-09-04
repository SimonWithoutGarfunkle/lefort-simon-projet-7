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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CurvePointControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private CurvePointService curvePointService;
	
	CurvePoint curvePoint;
	Integer id;
	
	@BeforeEach
	public void setUp() {
		curvePoint = new CurvePoint(99, 98d, 97d);
		curvePoint = curvePointService.addCurvePoint(curvePoint);
		id = curvePoint.getCurvePointId();		
		
	}
	
	@Test
	@WithMockUser
	public void homeTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser
	public void addCurveFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("curvePoint/add"));
	}
	
	@Test
	@WithMockUser
	public void validateCurveTest() throws Exception {	
		int resultBefore = curvePointService.getAllCurvePoints().size();
		Double myDouble = 1.0;
		mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate").with(csrf())
				.param("term", myDouble.toString())
				.param("value", myDouble.toString()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list")).andDo(print());
		
		int resultAfter = curvePointService.getAllCurvePoints().size();
		assertTrue(resultAfter == resultBefore + 1);
			
	}
	
	@Test
	@WithMockUser
	public void showUpdateFormTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/"+id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
		.andExpect(MockMvcResultMatchers.view().name("curvePoint/update"));
	}
	
	@Test
	@WithMockUser
	public void updateCurvePointTest() throws Exception {	
		Double myDouble = 3.0;
		mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/"+id).with(csrf())
				.param("curvePointId", id.toString())
				.param("value", myDouble.toString()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list")).andDo(print());
		CurvePoint result = curvePointService.getCurvePointById(id);
		assertEquals(myDouble , result.getValue());
		
	}
	
	@Test
	@WithMockUser
	public void deleteCurvePointTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/"+id))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
		
		assertNull(curvePointService.getCurvePointById(id));
	}

}
