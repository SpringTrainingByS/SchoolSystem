package pl.dn.schoolsystem.service.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse) res;
		
		
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, access-control-allow-origin");
		}
		if (response.getHeader("Access-Control-Expose-Headers") == null) {
			response.addHeader("Access-Control-Expose-Headers", "Authorization");
		}
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		}
		
		
		System.out.println("JWTAuthenticationFilter.doFilter ------------------------------------- ");
		
		Authentication authentication = TokenAuthenticationService
				.getAuthentication((HttpServletRequest) req);
		
		if (authentication == null) {
			System.out.println("authentication == null");
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, response);
	}
	
}
