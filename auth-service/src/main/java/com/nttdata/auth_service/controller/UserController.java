package com.nttdata.auth_service.controller;

import com.nttdata.auth_service.model.AuthRequest;
import com.nttdata.auth_service.model.AuthResponse;
import com.nttdata.auth_service.model.User;
import com.nttdata.auth_service.service.JwtService;
import com.nttdata.auth_service.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .filter(user -> jwtService.passwordMatches(authRequest.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")))
                .map(user -> {
                    String token = jwtService.generateToken(user.getUsername(), user.getRoles());
                    return new AuthResponse(token);
                });
    }

    @PostMapping("/register")
    public Mono<User> register(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/validate")
    public Mono<Map<String, Object>> validateToken(@RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return Mono.error(new RuntimeException("Token no proporcionado"));
        }
        try {
            String username = jwtService.validateTokenAndExtractSubject(token);
            Claims claims = jwtService.parseToken(token);
            return Mono.just(Map.of(
                    "status", "valid",
                    "username", username,
                    "roles", claims.get("roles")
            ));
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Token inválido: " + e.getMessage()));
        }
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwks() {
        // Obtén la clave pública del servicio JWT
        RSAPublicKey publicKey = (RSAPublicKey) jwtService.getPublicKey();

        // Construir un map compatible con el formato JWKS
        return Map.of(
                "keys", List.of(Map.of(
                        "kty", "RSA", // Tipo de clave
                        "use", "sig", // Uso de la clave (firma)
                        "alg", "RS256", // Algoritmo usado
                        "n", Base64.getUrlEncoder().encodeToString(publicKey.getModulus().toByteArray()), // Modulus
                        "e", Base64.getUrlEncoder().encodeToString(publicKey.getPublicExponent().toByteArray()) // Exponent
                ))
        );
    }
}
