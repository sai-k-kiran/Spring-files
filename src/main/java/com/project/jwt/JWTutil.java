package com.project.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class JWTutil {

    private final String SECRET_KEY = "123randomStringforKeySecret456ItShouldBeVeryRandomForMoreSecurity345";

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims){
        String JWTtoken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("htts://localhost:8080")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(Instant.now().plus(10, ChronoUnit.DAYS))
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return JWTtoken;
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
