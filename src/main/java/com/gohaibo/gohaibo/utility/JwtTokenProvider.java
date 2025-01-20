package com.gohaibo.gohaibo.utility;


import com.gohaibo.gohaibo.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {


    private final String SECRET_KEY;
    private final long jwtExpirationDate;

    public   JwtTokenProvider( String secretKey, @Value("${jwt.expiration.time}") long jwtExpirationDate) {
        this.SECRET_KEY = secretKey;
        this.jwtExpirationDate = jwtExpirationDate;
    }

    // generate JWT token
    public String generateToken(Authentication authentication){

        String email = authentication.getName();

        Date currentDate = new Date();

        //7 days
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token;
        try {
             token = Jwts.builder()
                    .subject(email)
                    .issuedAt(new Date())
                    .expiration(expireDate)
                    .signWith(key())
                    .compact();
        }catch (Exception e){
            throw  new AuthenticationException("Error generating token");
        }

        return token;
    }

    private SecretKey key(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // get username from JWT token
    public String getEmail(String token){

        return Jwts.parser()
                .verifyWith( key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }




}


//
//    // validate JWT token
//    public boolean validateToken(String token){
//        Jwts.parser()
//                .verifyWith((SecretKey) key())
//                .build()
//                .parse(token);
//        return true;
//
//    }
