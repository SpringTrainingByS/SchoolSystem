package pl.dn.schoolsystem.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 864_000_000; //10 DAYS
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	public static void addAuthentication(HttpServletResponse res, String username) {
		
		System.out.println("TokenAuthenticationService.addAuthentication()");
		System.out.println("Budowanie tokenu");
		
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		System.out.println("JWT: " + JWT.toString());
		
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		
		System.out.println("TokenAuthenticationService.getAuthentication()");
		
		String token = request.getHeader(HEADER_STRING);
		
		System.out.println("Pobranie headera authorization: " + token);
		
		
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			System.out.println("User: " + user);
			
			return user != null ?
					new UsernamePasswordAuthenticationToken(user, null, Collections.EMPTY_LIST) : null;
		}
		
		return null;
	}
}
