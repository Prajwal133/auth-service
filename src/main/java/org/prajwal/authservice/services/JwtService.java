package org.prajwal.authservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService implements CommandLineRunner {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiry}")
    private long expiration;

    /*
     * generates new jwt token based on payload/claims
     * @return
     */

    private String createToken(String username, long expiration, Map<String, Object> claims) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);// in milisecoonds
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email" , "prajwal@gmail.com");
        claims.put("role" , "Passenger");
        claims.put("phoneNumber" , "789456183");
        String token = createToken("Prajwal", expiration, claims);
        System.out.println("TOKEN is " + token);
    }
}
