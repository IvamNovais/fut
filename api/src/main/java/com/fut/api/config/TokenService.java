package com.fut.api.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fut.api.model.User;

@Service
public class TokenService {


    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256("coisa");
            return JWT.create()
                    .withIssuer("api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(getExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            return "deu ruim";
        }
    }

    public String validToken(String token){
        try{
        Algorithm algorithm = Algorithm.HMAC256("coisa");
        return JWT.require(algorithm)
            .withIssuer("api")
            .build()
            .verify(token)
            .getSubject();
        }
        catch(JWTVerificationException e){
            return "";
        }
    }

    private Instant getExpiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
