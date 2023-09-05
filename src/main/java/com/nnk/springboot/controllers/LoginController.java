package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String login() {
        return "login";
    }



	@GetMapping("/error")
	public String error(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String remoteUser = authentication.getName();
        model.addAttribute("remoteUser", remoteUser);
		String errorMessage = "You are not authorized for the requested data.";
		model.addAttribute("errorMsg", errorMessage);
		return "403";
	}
}
