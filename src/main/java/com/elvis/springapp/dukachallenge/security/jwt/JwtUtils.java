package com.elvis.springapp.dukachallenge.security.jwt;

import com.elvis.springapp.dukachallenge.security.ApplicationSecurityUser;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Component
@Data
@Slf4j
public class JwtUtils {
    @Value("${duka.app.security.jwt.secretKey}")
    private String secretKey;
//    @Value("${duka.app.security.jwt.jwtExpirationDays}")
//    private String expirationDays;
    public static final long JWT_TOKEN_VALIDITY = 5*60*60;


    public String generateJwtToken(Authentication authentication){
        ApplicationSecurityUser user = (ApplicationSecurityUser) authentication.getPrincipal();
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(Date.from(Instant.from(LocalDate.now().plusDays(Long.valueOf(expirationDays)))))
//                .claim("authorities", user.getAuthorities())
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .claim("authorities", user.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
