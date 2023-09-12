package com.explicit.redditCloneProject.Jwt;

import com.explicit.redditCloneProject.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    private UserRepository userRepository;
    @Value("${spring.jwt.secret}")
    private String JWT_SECRET;
    @Value("${spring.jwt.jwtExpirationTime}")
    private int JWT_EXPIRATION_TIME;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return generateTokenForUser(claims, username);
    }

    private String generateTokenForUser(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keybytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String extractUsernameFromToken(String theToken) {
        return extractClaim(theToken, Claims::getSubject);
    }

    public Date expirationTimeForToken(String theToken) {
        return extractClaim(theToken, Claims::getExpiration);
    }

    public Boolean validateToken(String TheToken, UserDetails userDetails) {
        final String username = extractUsernameFromToken(TheToken);
        return (username.equals(userDetails.getUsername()) && !isExpired(TheToken));
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isExpired(String theToken) {
        return expirationTimeForToken(theToken).before(new Date());
    }
}
