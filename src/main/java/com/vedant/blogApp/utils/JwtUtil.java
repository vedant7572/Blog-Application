package com.vedant.blogApp.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()  // Use parserBuilder instead of parser
                .setSigningKey(getSigningKey())  // Set the signing key
                .build()
                .parseClaimsJws(token)  // Use parseClaimsJws instead of parseSignedClaims
                .getBody();  // Get claims payload
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)  // Set custom claims
                .setSubject(subject)  // Set the subject (username, user ID, etc.)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Token issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Explicitly set algorithm
                .compact();
    }


    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


}