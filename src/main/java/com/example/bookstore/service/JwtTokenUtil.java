package com.example.bookstore.service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

        // previous code is not shown...

        @Value("${app.jwt.secret}")
        private String secretKey;

        private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

        public boolean validateAccessToken(String token) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException ex) {
                LOGGER.error("JWT expired", ex.getMessage());
            } catch (IllegalArgumentException ex) {
                LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
            } catch (MalformedJwtException ex) {
                LOGGER.error("JWT is invalid", ex);
            } catch (UnsupportedJwtException ex) {
                LOGGER.error("JWT is not supported", ex);
            } catch (SignatureException ex) {
                LOGGER.error("Signature validation failed");
            }

            return false;
        }

        public String getSubject(String token) {
            return parseClaims(token).getSubject();
        }

        private Claims parseClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }


}
