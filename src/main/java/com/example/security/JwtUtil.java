package com.example.security;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())   // ✅ SAME KEY
                .compact();
    }

    public String validateAndExtract(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // ✅ SAME KEY
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
