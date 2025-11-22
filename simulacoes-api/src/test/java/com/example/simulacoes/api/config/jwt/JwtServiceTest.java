package com.example.simulacoes.api.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;
    private final String TEST_SECRET = "12345678910Segredodetestekasdjfklwiefjwlefkdfjalskd";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService, 
                "secretKey",
                TEST_SECRET
            );

        userDetails = new User("test@example.com", "senha_hash", Collections.emptyList());
    }

    @Test
    void testGenerateTokenAndExtractUsername() {

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(token.length() > 0);

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userDetails.getUsername(), extractedUsername);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenValid_TokenExpired() {

        Key signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String expiredToken = Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new java.util.Date(System.currentTimeMillis() - 1000 * 60 * 60))
            .setExpiration(new java.util.Date(System.currentTimeMillis() - 1000 * 10))
            .signWith(signKey)
            .compact();

        assertFalse(jwtService.isTokenValid(expiredToken, userDetails));
    }
}