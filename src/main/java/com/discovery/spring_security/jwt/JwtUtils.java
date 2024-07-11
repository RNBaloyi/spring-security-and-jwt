package com.discovery.spring_security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")// these values are fetched from application file
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    Instant now = Instant.now();

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        // Extract JWT from Bearer token
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String generateTokenFromUsername(UserDetails userDetails){
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
//                .expiration(new Date((new Date()).getTime() +
//                        jwtExpirationMs))
                .expiration(Date.from(now.plus(Long.parseLong(jwtExpirationMs), ChronoUnit.MILLIS)))
                .signWith(key())
                .compact();
    }
    public String getUserNameFromJwtToken(String token){ //abstracting the username or decoding
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().
                    parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}