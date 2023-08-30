package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameException;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {
	
	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	private static Logger logger = LoggerFactory.getLogger(RuleNameService.class);
	
	/**
	 * Extract all Rules from the Databse
	 * 
	 * @return List of all Rules from the Databse
	 */
	public List<RuleName> getAllRules() {
		logger.info("call getAllRule");
		return ruleNameRepository.findAll();
	}
	
	public RuleName convertOptionalToRule(Optional<RuleName> option) {
		logger.info("call convertOptionalToRule");
		return option.orElse(null);
		
	}
	
	public RuleName getRuleNameById(Integer id) {
		logger.info("call getRuleNameById");		
		return convertOptionalToRule(ruleNameRepository.findById(id));		
	}
	
	/**
	 * Add RuleName to DB
	 * 
	 * @param ruleName
	 * @return saved ruleName
	 */
	public RuleName addRuleName(RuleName ruleName) {
		logger.info("call addRuleName");	
		return ruleNameRepository.save(ruleName);
	}
	
	
	/**
	 * Update an existing RuleName
	 * 
	 * @param RuleName
	 * @return updated RuleName
	 */
	public RuleName updateRuleName(RuleName ruleName) {
		logger.info("call updateRating");	
		return ruleNameRepository.save(ruleName);
	}
	
	/**
	 * Delete the RuleName corresponding to the id
	 * 
	 * @param id of the RuleName to delete
	 */
	public void deleteRuleName(Integer id) {
		logger.info("call deleteRuleName");	
		ruleNameRepository.deleteById(id);
		
	}
	
	
	/**
	 * Call the data validation method for the restricted attributes of the RuleName.
	 * Here only Name and Json are mandatory with at least 2 characters
	 * 
	 * @param ruleName to validate
	 */
	public void validateRuleName(RuleName ruleName) {
		validateStringRuleName(ruleName.getName(),"Name", 2);
		validateStringRuleName(ruleName.getJson(),"Json", 2);
				
	}
	
	/**
	 * Check the data validity 
	 * 
	 * @param value to test
	 * @param fieldName
	 * @param min string lenghth requiered
	 */
	private void validateStringRuleName(String value, String fieldName, int min) {
		if (value == null || value.trim().length()<min ) {
			throw new RuleNameException(fieldName + " must be a text of at least " + min + " characters.");
		}				
	}

}
