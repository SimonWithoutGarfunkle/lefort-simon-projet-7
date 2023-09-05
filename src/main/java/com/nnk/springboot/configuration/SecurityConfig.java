package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.nnk.springboot.service.MyUserDetailsService;

/**
 * Configure the security of the application
 * @author Simon
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/**
	 * Configures the HTTP security filters for the application. This method defines security rules,
	 * security filters, and authentication providers for the application's security configuration.
	 *
	 * @param http The HTTP security configuration.
	 * @return The configured security filter chain.
	 * @throws Exception If an error occurs during configuration.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((req) -> req.requestMatchers("/bidList/**").authenticated()
				.requestMatchers("/curvePoint/**").authenticated()
				.requestMatchers("/rating/**").authenticated()
				.requestMatchers("/trade/**").authenticated()
				.requestMatchers("/rule/**").authenticated()
				.requestMatchers("/rule/**").authenticated()
				.requestMatchers("/user/**").hasRole("ADMIN")
				.requestMatchers("/error").permitAll()
				.anyRequest().permitAll())											
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/bidList/list")
		                .failureUrl("/login?error"))						                
		        .logout((logout) ->
							logout.logoutUrl("/app-logout")
								.deleteCookies("remove")
								.logoutSuccessUrl("/login")
								.invalidateHttpSession(true)
								.clearAuthentication(true)
								.permitAll())
		        .exceptionHandling(exceptionHandling -> exceptionHandling
		                .accessDeniedPage("/403"));
							                
		return http.build();      
							                
							                
	}
	
	
	/**
	 * Configure the authentification with the selected encryption for password and the user profil used for the login
	 * 
	 * @return configured authentification service
	 */
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	
	/**
	 * Configure the custom user service
	 * 
	 * @return configured user service
	 */
	@Bean
    public MyUserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }
	
	/**
	 * Configure the password encryption for the users
	 * 
	 * @return configured encryption service
	 */
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 

}
