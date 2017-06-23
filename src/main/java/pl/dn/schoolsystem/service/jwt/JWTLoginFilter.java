package pl.dn.schoolsystem.service.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.service.UserService;
import pl.dn.schoolsystem.service.impl.UserServiceImpl;

@ComponentScan
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

	@Autowired
	UserService userService = new UserServiceImpl();
	
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException,
			IOException, ServletException {
		
		System.out.println("Jestem w JwtLogginFilter.attemptAuthentication -------------------------------------");
		
		AccountCredentials creds = new ObjectMapper()
			.readValue(req.getInputStream(), AccountCredentials.class);
		
		User user = userService.findByUsername(creds.getUsername());
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getUsername(),
						creds.getPassword(),
						user.getAuthorities()
						)
		);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("Jestem w JWTLogginFilter.successfulAuthentication -------------------------------------- ");
		
		System.out.println("authResult.getName(): " + authResult.getName());
		TokenAuthenticationService.addAuthentication(response, authResult.getName());
		
	}
	
	

}
