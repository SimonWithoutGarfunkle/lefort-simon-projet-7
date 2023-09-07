package com.nnk.springboot.serviceTest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;

public class RuleNameServiceTest {
	
	@Mock
	private RuleNameRepository ruleNameRepository;
	
	@InjectMocks
	private RuleNameService ruleNameService;
	
	private RuleName rule;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		rule = new RuleName();
		rule.setRuleNameId(1);
		rule.setJson("jsonTest");
		when(ruleNameRepository.save(rule)).thenReturn(rule);
		
	}
	
	@Test
	void testConvertOptionalToRuleNameNonNullValue() {
		//Arrange
        Optional<RuleName> optionalRule = Optional.of(rule);

        //Act
        RuleName result = ruleNameService.convertOptionalToRule(optionalRule);

        //Assert
        assertEquals(rule, result);
	}
	
	@Test
	void testConvertOptionalToRuleNameWithNullValue() {
		//Arrange
        Optional<RuleName> optionalRule = Optional.empty();

        //Act
        RuleName result = ruleNameService.convertOptionalToRule(optionalRule);

        //Assert
        assertNull(result);
	}
	
	@Test
	void testGetRuleNameById() {
        //Arrange
        Optional<RuleName> optionalRuleName = Optional.ofNullable(rule);
    	when(ruleNameRepository.findById(anyInt())).thenReturn(optionalRuleName);
    	
    	//Act
    	RuleName result = ruleNameService.getRuleNameById(1);

        //Assert
    	verify(ruleNameRepository).findById(1);
    	assertEquals(rule, result);		
	}
	
    @Test
    void testUpdateRuleName() {
        //Act
    	RuleName updatedRuleName = ruleNameService.updateRuleName(rule);

        //Assert
        verify(ruleNameRepository).save(rule);
        assertEquals("jsonTest", updatedRuleName.getJson());
        
    }

    @Test
    void testDeleteRuleName() {
        //Act
    	ruleNameService.deleteRuleName(1);

        //Assert
    	verify(ruleNameRepository).deleteById(1);
    }
    
    @Test
    void testGetAllRules() {
        // Arrange
        RuleName rule1 = new RuleName();
        rule1.setRuleNameId(1);
        RuleName rule2 = new RuleName();
        rule2.setRuleNameId(2);
        List<RuleName> rules = Arrays.asList(rule1, rule2);
        when(ruleNameRepository.findAll()).thenReturn(rules);

        // Act
        List<RuleName> result = ruleNameService.getAllRules();

        // Assert
        assertEquals(rules.size(), result.size());
        assertEquals(rules.get(0), result.get(0));
        assertEquals(rules.get(1), result.get(1));
    }

    @Test
    void testAddRuleName() {
        // Arrange
        RuleName newRule = new RuleName();
        newRule.setRuleNameId(2);
        newRule.setJson("newJsonTest");
        when(ruleNameRepository.save(newRule)).thenReturn(newRule);

        // Act
        RuleName addedRule = ruleNameService.addRuleName(newRule);

        // Assert
        verify(ruleNameRepository).save(newRule);
        assertEquals(newRule, addedRule);
    }

    @Test
    void testValidateRuleName_ValidData() {
        // Arrange
        RuleName validRule = new RuleName();
        validRule.setName("ValidName");
        validRule.setJson("ValidJson");

        // Act & Assert
        assertDoesNotThrow(() -> ruleNameService.validateRuleName(validRule));
    }

    @Test
    void testValidateRuleName_InvalidName() {
        // Arrange
        RuleName invalidRule = new RuleName();
        invalidRule.setName("I");
        invalidRule.setJson("ValidJson");

        // Act & Assert
        assertThrows(RuleNameException.class, () -> ruleNameService.validateRuleName(invalidRule));
    }

    @Test
    void testValidateRuleName_InvalidJson() {
        // Arrange
        RuleName invalidRule = new RuleName();
        invalidRule.setName("ValidName");
        invalidRule.setJson("I");

        // Act & Assert
        assertThrows(RuleNameException.class, () -> ruleNameService.validateRuleName(invalidRule));
    }
	
	

}
