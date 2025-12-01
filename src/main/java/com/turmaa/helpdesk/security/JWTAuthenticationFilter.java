package com.turmaa.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; // <--- NOVO IMPORT
import java.util.stream.Collectors; // <--- NOVO IMPORT

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority; // <--- NOVO IMPORT
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turmaa.helpdesk.domain.dtos.CredenciaisDTO;

/**
 * Filtro de autenticação JWT, responsável por interceptar e processar
 * as tentativas de login no endpoint '/login'.
 *
 * <p>Extende {@link UsernamePasswordAuthenticationFilter} para se integrar
 * ao fluxo de autenticação padrão do Spring Security.</p>
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /** Gerenciador de autenticação do Spring Security, injetado via construtor. */
    private final AuthenticationManager authenticationManager;

    /** Utilitário responsável por gerar os tokens JWT. */
    private final JWTUtil jwtUtil;

    /**
     * Construtor que recebe as dependências necessárias.
     *
     * @param authenticationManager O gerenciador de autenticação do Spring.
     * @param jwtUtil O nosso utilitário para manipulação de tokens.
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Este método é o primeiro a ser executado ao bater no endpoint /login.
     * Ele tenta realizar a autenticação.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // 1. Lê o JSON do corpo da requisição e converte para nosso DTO de credenciais.
            CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);

            // 2. Cria o token de autenticação do Spring (ainda não autenticado).
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

            // 3. Delega ao AuthenticationManager a tarefa de autenticar.
            // (Ele usará o UserDetailsServiceImpl e o BCryptPasswordEncoder).
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
            
        } catch (Exception e) {
            // Se houver falha (ex: JSON mal formatado), lança uma exceção.
            throw new RuntimeException(e);
        }
    }

    /**
     * Chamado pelo Spring Security se o 'attemptAuthentication' for bem-sucedido.
     * Gera o token JWT e o adiciona na resposta.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 1. Pega o "Principal" (o usuário autenticado) e o converte para nossa classe UserSS.
        UserSS user = ((UserSS) authResult.getPrincipal());
        String username = user.getUsername();
        Integer id = user.getId(); 
        
        // --- 2. NOVO: Extrair as roles do usuário autenticado ---
        List<String> roles = user.getAuthorities().stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toList());
        // --------------------------------------------------------

        // 3. Gera o token JWT usando nosso utilitário, agora passando o ID e as ROLES.
        String token = jwtUtil.generateToken(username, id, roles); // <--- ATUALIZADO

        // 4. Expõe o cabeçalho 'Authorization' para que o front-end possa lê-lo.
        response.setHeader("access-control-expose-headers", "Authorization");
        
        // 5. Adiciona o token JWT no cabeçalho da resposta com o prefixo "Bearer ".
        response.setHeader("Authorization", "Bearer " + token);
    }

    /**
     * Chamado pelo Spring Security se o 'attemptAuthentication' falhar (ex: senha inválida).
     * Retorna uma resposta 401 Unauthorized padronizada.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Status 401
        response.setContentType("application/json");
        response.getWriter().append(json()); // Escreve o corpo do erro JSON
    }

    /**
     * Método auxiliar para criar o corpo da resposta de erro JSON.
     */
    private CharSequence json() {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\""
                + "}";
    }
}