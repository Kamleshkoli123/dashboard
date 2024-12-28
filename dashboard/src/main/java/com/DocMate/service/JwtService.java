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
    
    @Value("${admin.key}") // admin key
    private String adminKey;

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
    
    public boolean validateAdminToken(String token) {
        try {
        	token = token.split(" ")[1];
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            logger.info("fetched role from token {}", role);
            if (!adminKey.equals(role)) {
                logger.warn("Non-admin token provided: {}", token);
                return false;
            }
            logger.info("Admin token validated successfully.");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", token);
            return false;
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", token);
            return false;
        }
    }
    
    public String extractRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("role", String.class);
        } catch (Exception e) {
            logger.error("Error extracting role from token: {}", token, e);
            throw new RuntimeException("Invalid token");
        }
    }

}//The method parserBuilder() is undefined for the type Jwts
