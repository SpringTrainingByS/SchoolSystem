package pl.dn.schoolsystem.service.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.dn.schoolsystem.model.security.Authority;

public class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 864_000_000; //24 hours
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	static final String ROLES = "roles";
	
	public static void addAuthentication(HttpServletResponse res, Authentication authResult) {
		
		System.out.println("TokenAuthenticationService.addAuthentication()");
		System.out.println("Budowanie tokenu");
		
		String username = authResult.getName();
		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
		
		System.out.println("authorities length: " + authorities.size());
		
		authorities.forEach(s -> System.out.println(s.getAuthority()));
		
		Claims claims = Jwts.claims().setSubject(username);
		claims.put(ROLES, authorities.stream().map(s -> s.getAuthority()).collect(Collectors.toList()));
		
		
		String JWT = Jwts.builder()
				.setClaims(claims)
//				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		System.out.println("JWT: " + JWT.toString());
		
		//System.out.println("Dodanie nagłówków do odpowiedzi");
		
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		
//		if (res.getHeader("Access-Control-Allow-Origin") == null) {
//			res.addHeader("Access-Control-Allow-Origin", "*");
//		}
//		if (res.getHeader("Access-Control-Allow-Headers") == null) {
//			res.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, access-control-allow-origin");
//		}
//		if (res.getHeader("Access-Control-Expose-Headers") == null) {
//			res.addHeader("Access-Control-Expose-Headers", "Authorization");
//		}
		
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		
		System.out.println("TokenAuthenticationService.getAuthentication()");
		
		String token = request.getHeader(HEADER_STRING);
		
		System.out.println("Pobranie headera authorization: " + token);
		
		
		if (token != null) {
//			String user = Jwts.parser()
//					.setSigningKey(SECRET)
//					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//					.getBody()
//					.getSubject();
			
			Claims claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
			
			String username = claims.getSubject();
			
			String[] roles = Arrays.asList(claims.get(ROLES)).toString().split(",");
			
			//System.out.println("role: " + claims.get(ROLES).toString());
			
			System.out.println("Role pobrane dla użytkownika " + username + ": ");
			for (String role : roles ) {
				System.out.println(role);
			}
			
			Collection<? extends GrantedAuthority> authorities =
					Arrays.asList((claims.get(ROLES)).toString().split(",")).stream()
	                .map(authority -> new Authority(authority.replaceAll("[\\[,\\]]", "")))
	                .collect(Collectors.toList());
			
			System.out.println("User: " + username);
			
			System.out.println("Role po przeparsowaniu: ");
			authorities.forEach(a -> System.out.println(a.getAuthority()));
			
			return username != null ?
					new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
		}
		
		return null;
	}
}
