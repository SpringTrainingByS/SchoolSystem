package pl.dn.schoolsystem.service.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import pl.dn.schoolsystem.controller.TestController;


public class JWTAuthenticationFilter extends GenericFilterBean {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		LOG.debug("JWTAuthenticationFilter.doFilterr -----------------------------------------------");
		
		HttpServletResponse response = (HttpServletResponse) res;
		
		Authentication authentication = TokenAuthenticationService
				.getAuthentication((HttpServletRequest) req);
		
		if (authentication == null) {
			LOG.debug("authentication == null");
			throw new IOException("Authentication header is null");
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, response);
	}
	
}
