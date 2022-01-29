package com.boot.blog.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTtokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date date = new Date();
        Date expireDate = new Date(date.getTime()+jwtExpirationDate);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();
        return token;
    }
    //get username from token
    public String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    //Validate JWT token

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Error,Invalid JWT token");
        }
    }

}
