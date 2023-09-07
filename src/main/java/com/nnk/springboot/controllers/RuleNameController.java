package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RuleNameException;
import com.nnk.springboot.service.RuleNameService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class RuleNameController {
	
    @Autowired
    private RuleNameService ruleNameService;
    
    private static Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String remoteUser = authentication.getName();
        model.addAttribute("remoteUser", remoteUser);
        
        List<RuleName> rules = ruleNameService.getAllRules();
        model.addAttribute("ruleNames", rules);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        try {
        	ruleNameService.validateRuleName(ruleName);
        } catch (RuleNameException e) {
        	logger.error(e.getMessage());
    		model.addAttribute("errorMessage", e.getMessage());
    		return "ruleName/add";
        	
        }
        
        ruleNameService.addRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleToUpdate = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleToUpdate);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	try {
        	ruleNameService.validateRuleName(ruleName);
        } catch (RuleNameException e) {
        	logger.error(e.getMessage());
    		model.addAttribute("errorMessage", e.getMessage());
    		return "ruleName/update";
        	
        }
        ruleNameService.updateRuleName(ruleName);
    	return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
