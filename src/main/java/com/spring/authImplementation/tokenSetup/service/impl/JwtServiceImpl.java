package com.spring.authImplementation.tokenSetup.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtServiceImpl {

    // **************** Authentication Mechanism - JWT ******************** //
    // Replace this with a secure key in a real application, ideally fetched from environment variables
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // 3 Steps Process -> Header, Payload, Verify Signature:::
    //https://jwt.io/


    // Generate token with given user name
    public String generateToken(String userName) {
        Map <String, Object> claimsJwt = new HashMap<>();
        return createToken(claimsJwt, userName);
    }

    // Create a JWT token with specified claims and subject (user name) -> returns a token
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30)) // valid = 30 mins
                .signWith(generateSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // Get the signing key for JWT token
    private Key generateSignKey() {
        byte[] keyInBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyInBytes);
    }


    // ================ Authorization =============================//
    // Extract the below from the token
/*  1) username = (subject)
    2) IssuedDate
    3) ExpiredDate - Make sure its valid?
    4) */

    public String extractUsername(String token) { // Gives the username::
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) { // Gives the expiration Date::
        return extractClaim(token, Claims::getExpiration);
    }

    //Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
