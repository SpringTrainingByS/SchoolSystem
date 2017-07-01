package pl.dn.schoolsystem.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pl.dn.schoolsystem.service.impl.UserSecurityService;
import pl.dn.schoolsystem.service.jwt.JWTAuthenticationFilter;
import pl.dn.schoolsystem.service.jwt.JWTLoginFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	
	private static final String Salt = "salt"; // should be protected better
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(Salt.getBytes()));
	}
	
	@Bean
	public JWTLoginFilter jwtLoginFilter() throws Exception {
		return new JWTLoginFilter("/login", authenticationManager());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().
		authorizeRequests().antMatchers("/").permitAll()
		.antMatchers(HttpMethod.POST, "/login").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		
		.and()
		
//		.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
//				UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(jwtLoginFilter(), 
				UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
	
}