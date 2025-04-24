package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
					//disable csrf
					.csrf(customizer -> customizer.disable())
					//authorize any request, but not storing the username and pwd
					.authorizeHttpRequests(request -> request
							 .requestMatchers("/register","/login").permitAll()	// allow public access
							.anyRequest().authenticated())
					// showing the form login(for BROWSER), but postman will show form in response body
//					.formLogin(Customizer.withDefaults())
					//to show the response in postman , for REST APIs
					.httpBasic(Customizer.withDefaults())
					//make stateless session
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
//		Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//			
//			@Override
//			public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//				customizer.disable();
//			}
//		};
//		httpSecurity.csrf(custCsrf);
		
		return httpSecurity.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//disabling the default password checking
		provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		// the below code is valid bcz .withDefPasEnc() is a method of User
//		// that returns an instance of UserBuilder. UserBuilder is inner static class of User
//		// If you remove withDefPasEnc() the code will be error as outer class cant call inner class meth
//		
//		UserDetails user1 = User
//				.withDefaultPasswordEncoder()
//				.username("Arjun")
//				.password("pwd1")
//				.roles("USER")
//				.build();
//		UserDetails user2 = User
//				.withDefaultPasswordEncoder()
//				.username("Brian")
//				.password("pwd2")
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1, user2);
//	}
	
}
