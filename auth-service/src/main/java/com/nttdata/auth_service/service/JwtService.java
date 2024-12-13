package com.nttdata.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); // Generar par de claves pública y privada
    private final PasswordEncoder passwordEncoder;

    public JwtService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Generar token firmado con la clave privada
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256) // Usar clave privada para firmar
                .compact();
    }

    // Validar token y extraer el "subject" (normalmente el username) usando la clave pública
    public String validateTokenAndExtractSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic()) // Usar clave pública para validar la firma
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // Devuelve el "sub" del token (normalmente el username)
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido", e);
        }
    }

    // Extraer las claims completas del token
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener la clave pública
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    // Validar contraseñas
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
