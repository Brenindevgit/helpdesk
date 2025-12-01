package com.turmaa.helpdesk.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Filtro de autorização JWT.
 * Este filtro intercepta todas as requisições (exceto /login) para verificar
 * se o token JWT enviado no cabeçalho é válido e, em caso afirmativo,
 * autentica o usuário para aquela requisição.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    /**
     * Construtor que recebe as dependências necessárias para a autorização.
     *
     * @param authenticationManager O gerenciador de autenticação (herdado do Spring).
     * @param jwtUtil Nosso utilitário para validar o token.
     * @param userDetailsService Nosso serviço para buscar os detalhes do usuário no banco.
     */
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Método principal do filtro, executado em toda requisição.
     * Ele lê o cabeçalho Authorization, valida o token e define o usuário
     * autenticado no contexto de segurança do Spring.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 1. Pega o conteúdo do cabeçalho "Authorization" da requisição.
        String header = request.getHeader("Authorization");
        
        // 2. Verifica se o cabeçalho existe e se começa com "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            
            // 3. Extrai o token (remove o "Bearer ") e tenta obter a autenticação.
            UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7));
            
            if (authToken != null) {
                // 4. Se o token for válido, define o usuário como autenticado no contexto do Spring Security.
                // É isso que permite o @PreAuthorize funcionar.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 5. Continua a execução da cadeia de filtros (permite que a requisição chegue ao Controller).
        chain.doFilter(request, response);
    }

    /**
     * Método auxiliar que valida o token e, se for válido,
     * busca o usuário no banco e retorna um token de autenticação.
     *
     * @param token A string pura do token JWT.
     * @return Um UsernamePasswordAuthenticationToken (com usuário e permissões) ou null.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // 1. Usa o JWTUtil para verificar a assinatura e a expiração do token.
        if (jwtUtil.tokenValido(token)) {
            // 2. Se for válido, extrai o e-mail (username) de dentro do token.
            String username = jwtUtil.getUsername(token);
            
            // 3. Usa o UserDetailsService para buscar os dados do usuário no banco.
            UserDetails details = userDetailsService.loadUserByUsername(username);
            
            // 4. Retorna um token de autenticação do Spring com os detalhes do usuário e suas permissões (authorities).
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        }
        // Se o token não for válido, retorna nulo.
        return null;
    }
}