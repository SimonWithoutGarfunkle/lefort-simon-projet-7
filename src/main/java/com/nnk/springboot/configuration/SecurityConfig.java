package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.nnk.springboot.service.MyUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((req) -> req.requestMatchers("/dashboard").authenticated()
				.anyRequest().permitAll())											
							                .formLogin(withDefaults());                
							                
		return http.build();      
							                
							                
	}
	
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());         
        return authProvider;
    }
	
	@Bean
    public MyUserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

}
