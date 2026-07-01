package com.example.insuranceplatform.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@ConfigurationProperties(prefix = "security")
@Data
@Slf4j
public class JwtTokenUtil {
    private String secret;
    private Integer expiration;

    private Key signKey(){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .subject(email)
                .signWith(signKey())
                .issuer("Doctor platform")
                .issuedAt(new Date())
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return claimsFromToken.getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        }catch (Exception e){
            log.info("Invalid token");
        }

        return false;
    }
}
