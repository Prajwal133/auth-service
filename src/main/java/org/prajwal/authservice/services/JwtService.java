package org.prajwal.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService implements CommandLineRunner {

    private static String SECRET_KEY;

    public JwtService(@Value("${jwt.secret}") String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Value("${jwt.expiry}")
    private long expiration;

    /*
     * generates new jwt token based on payload/claims
     * @return
     */

    public String createToken(String email, Map<String, Object> claims) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);// in milisecoonds

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    private static SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(JwtService.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public Claims extractAllClaims(String token) {
        // Extract claims after signature verification
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /*
     * checks if token expiry was before the current time stamp or not
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String email) {
        final String extractedEmailFromToken = extractEmail(token);
        return extractedEmailFromToken.equals(email) && !isTokenExpired(token);
    }

    /*
     to extract any payload we want
     */
    public Object extractPayload(String token, String payLoadKey) {
        Claims claim = extractAllClaims(token);
        return (Object) claim.get(payLoadKey);
    }

    // just for testing purpose on CLI
    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "Prajwal");
        claims.put("role", "Passenger");
        claims.put("phoneNumber", "789456183");
        String token = createToken("prajwal@gmail.com",claims);
        System.out.println("TOKEN is " + token);
        System.out.println("is Token Expired? " + isTokenExpired(token));
        System.out.println("Email address is " + extractEmail(token));
        System.out.println("is token Valid : " + validateToken(token, "prajwal@gmail.com"));
        System.out.println("is token Valid : " + validateToken(token, "298hfuien@gmail.com"));

    }
}
