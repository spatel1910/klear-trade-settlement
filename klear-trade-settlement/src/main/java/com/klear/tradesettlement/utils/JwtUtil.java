package com.klear.tradesettlement.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


    @Value("${jwt.secret.key}")
    private String secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 10;
    Map<String,Object> claims=new HashMap<>();
    public String generateToken(String username) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token, String username) {
        final String extractedUsername = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
