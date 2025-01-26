package com.surveyProject.project.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	
	private final Key key;

    @Value("36000000")
    private int jwtExpirationMs;
    
    public int getExpiration() {
        return jwtExpirationMs;
    }
    
    
    public JwtProvider(@Value("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890}") String secret, @Value("36000000") int jwtExpirationMs) {
    	System.out.println(secret);
    	System.out.println(jwtExpirationMs);
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
    }
    
    public String generateJwtToken(String email) {
        return Jwts.builder()
                .claim("userEmail", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String bearerToken) {
    	if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
    		throw new RuntimeException("Invalid JWT token format");
    	}
    	return bearerToken.substring("Bearer ".length());
    }
    
    public String getUserIdFromJwt(String token) {
    	Claims claims = getClaims(token);
    	return claims.get("username", String.class);
    }
    
    public boolean isValidToken(String token) {
    	try {
    		getClaims(token);
    		return true;
    	} catch (Exception e) {
    		return false;
		}
    }

	private Claims getClaims(String token) {
		JwtParser jwtParser = Jwts.parserBuilder()
				.setSigningKey(key)
				.build();
		
		return jwtParser.parseClaimsJws(token).getBody();
	} 
    
}
