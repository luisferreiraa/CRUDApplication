package com.example.CRUDApplication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.CRUDApplication.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("CRUDApplication")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm)
                    .withIssuer("CRUDApplication")
                    .build();

            var decodedJWT = verifier.verify(token);

            // Recupera o username e o role do JWT
            String username = decodedJWT.getSubject(); // Esse é o username
            String role = decodedJWT.getClaim("role").asString(); // Esse é o role

            // Agora, você pode retornar tanto o username quanto o role
            return username;

        } catch(JWTVerificationException exception) {
            return null; // Retorne null se o token não for válido
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+00:00"));
    }
}
