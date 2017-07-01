package pl.dn.schoolsystem.service.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.service.UserService;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

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
		
		System.out.println("Jestem w JwtLogginFilter.attemptAuthentication -------------------------------------");
		
		//System.out.println("Dodawanie nagłówków do odpowiedzi.");
		
//		if (res.getHeader("Access-Control-Allow-Origin") == null) {
//			res.addHeader("Access-Control-Allow-Origin", "*");
//		}
//		if (res.getHeader("Access-Control-Allow-Headers") == null) {
//			res.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, access-control-allow-origin");
//		}
//		if (res.getHeader("Access-Control-Expose-Headers") == null) {
//			res.addHeader("Access-Control-Expose-Headers", "Authorization");
//		}
		
		if (req.getInputStream() == null) {
			throw new ServletException("request input stream() is null");
		}
		
		AccountCredentials creds;
//		= new ObjectMapper()
//			.readValue(req.getInputStream(), AccountCredentials.class);
		
		try {
			creds = new ObjectMapper()
					.readValue(req.getInputStream(), AccountCredentials.class);
		}
		catch (Exception e) {
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
		
		System.out.println("Jestem w JWTLogginFilter.successfulAuthentication -------------------------------------- ");
		
		System.out.println("authResult.getName(): " + authResult.getName());
		
		TokenAuthenticationService.addAuthentication(response, authResult);
		
	}
	
	

}
