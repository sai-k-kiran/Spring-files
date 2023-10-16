package com.project.jwt;

import io.jsonwebtoken.Claims;
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

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String issueToken(String subject) {return issueToken(subject, Map.of());}

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims){
        String JWTtoken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("htts:saik.com   ")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(Instant.now().plus(10, ChronoUnit.DAYS))
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return JWTtoken;
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public boolean isValidToken(String token, String username) {
        String subject = getSubject(token);

        return (subject.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }
}
