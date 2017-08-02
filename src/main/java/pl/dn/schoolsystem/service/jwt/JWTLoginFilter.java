package pl.dn.schoolsystem.service.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.dn.schoolsystem.controller.TestController;
import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.service.UserService;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger LOG = LoggerFactory.getLogger(JWTLoginFilter.class);

	@Autowired
	UserService userService; 
	
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException,
			IOException, ServletException {
		
		//System.out.println("Jestem w JwtLogginFilter.attemptAuthentication -------------------------------------");
		LOG.debug("Jestem w JwtLogginFilter.attemptAuthentication -------------------------------------");

		
		if (req.getInputStream() == null) {
			LOG.debug("Request input stream in null");
			throw new ServletException("request input stream() is null");
		}
		
		AccountCredentials creds;

		
		try {
			creds = new ObjectMapper()
					.readValue(req.getInputStream(), AccountCredentials.class);
		}
		catch (Exception e) {
			LOG.debug("");
			return null;
		}
		
		
		
		User user = userService.findByUsername(creds.getUsername());
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getUsername(),
						creds.getPassword(),
						 user != null ? user.getAuthorities() : null
						)
		);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		LOG.info("authResult.getName(): " + authResult.getName());
		//System.out.println("authResult.getName(): " + authResult.getName());
		
		TokenAuthenticationService.addAuthentication(response, authResult);
		
	}
	
	

}
