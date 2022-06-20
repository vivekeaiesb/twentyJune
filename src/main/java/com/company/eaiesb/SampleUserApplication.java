package com.company.eaiesb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.company.eaiesb.filter.JwtRequestFilter;

@SpringBootApplication
public class SampleUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleUserApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Configuration
	@EnableWebSecurity
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {


		@Autowired
		private JwtRequestFilter jwtFilter;
		
			
		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.cors().and().csrf().disable()
					.authorizeRequests().antMatchers("/api/v1/login").permitAll().
							anyRequest().authenticated().and().
							exceptionHandling().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		}
		}

}
