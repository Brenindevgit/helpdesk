package com.turmaa.helpdesk.security;

import java.util.Date;
import java.util.List; // <--- 1. IMPORTANTE: Adicionado import de List

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe utilitária para operações com JWT (JSON Web Token).
 * Responsável por gerar tokens, validar tokens e extrair informações deles.
 */
@Component
public class JWTUtil {

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Gera um novo token JWT para um usuário.
     * * @param email O e-mail do usuário (subject).
     * @param id O ID do usuário no banco.
     * @param roles A lista de perfis do usuário (ex: ["ROLE_ADMIN", "ROLE_TECNICO"]).
     * @return O token assinado.
     */
    // 2. ALTERAÇÃO: Adicionado o parâmetro 'List<String> roles'
    public String generateToken(String email, Integer id, List<String> roles) {
        return Jwts.builder()
                   .setSubject(email)
                   .claim("id", id)
                   .claim("roles", roles) // <--- 3. NOVO: Adiciona os perfis ao token
                   .setExpiration(new Date(System.currentTimeMillis() + expiration))
                   .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                   .compact();
    }

    /**
     * Verifica se um token JWT é válido.
     */
    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            
            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extrai o nome de usuário (e-mail) de dentro do token.
     */
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
    
    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}