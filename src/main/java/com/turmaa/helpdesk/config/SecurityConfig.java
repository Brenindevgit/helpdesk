package com.turmaa.helpdesk.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.turmaa.helpdesk.security.JWTAuthenticationFilter;
import com.turmaa.helpdesk.security.JWTAuthorizationFilter;
import com.turmaa.helpdesk.security.JWTUtil;

/**
 * Classe de configuração central do Spring Security.
 * @EnableWebSecurity habilita a segurança web na aplicação.
 * @EnableGlobalMethodSecurity(prePostEnabled = true) habilita a segurança baseada em anotações
 * (como @PreAuthorize) nos controllers.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Lista de URLs que serão sempre públicas.
    private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };
    // Lista de URLs que serão públicas apenas para requisições POST.
    private static final String[] PUBLIC_MATCHERS_POST = { "/clientes", "/tecnicos" };

    @Autowired
    private Environment env; // Usado para checar o perfil (profile) ativo.
    @Autowired
    private JWTUtil jwtUtil; // Nosso utilitário de token.
    @Autowired
    private UserDetailsService userDetailsService; // Nosso serviço de busca de usuário.

    /**
     * Configura as regras de segurança HTTP (autorização, filtros, sessão, etc.).
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Libera o acesso ao console do H2 se o perfil "test" estiver ativo.
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // Habilita o CORS (definido no @Bean abaixo) e desabilita o CSRF,
        // pois não usamos sessões/cookies (somos stateless).
        http.cors().and().csrf().disable();
        
        // Adiciona nosso filtro de autenticação customizado, que vai "ouvir" o endpoint /login.
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        
        // Adiciona nosso filtro de autorização, que vai verificar o token em todas as outras requisições.
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        
        // Define as regras de permissão de acesso.
        http.authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS).permitAll() // Libera as rotas do H2.
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // Libera o cadastro de clientes e técnicos.
            .anyRequest().authenticated(); // Exige autenticação para todo o resto.
            
        // Define a política de gerenciamento de sessão como STATELESS (sem estado),
        // pois usamos JWT e não sessões HTTP.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Configura como o Spring Security deve realizar a autenticação.
     * Informa ao Spring qual é o serviço de busca de usuário (UserDetailsService)
     * e qual é o algoritmo de criptografia de senha (BCryptPasswordEncoder).
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * Define a configuração de CORS (Cross-Origin Resource Sharing) para a aplicação.
     * Permite que um front-end (rodando em outro domínio/porta) possa acessar esta API.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Cria um @Bean de BCryptPasswordEncoder.
     * Isso torna o encoder disponível para ser injetado (@Autowired)
     * em qualquer parte da aplicação (como nos nossos Services).
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}