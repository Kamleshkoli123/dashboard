package com.DocMate.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:2592000000}") // Default 30 days in milliseconds
    private long jwtExpiration;

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            logger.info("passkey: {}", secretKey);
            
            // Ensure only the token part is extracted
            String jwt = token.split(" ")[1];
            
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(jwt);
            
            logger.info("JWT token validated successfully.");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", token);
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", token);
        }
        return false;
    }


}//The method parserBuilder() is undefined for the type Jwts
