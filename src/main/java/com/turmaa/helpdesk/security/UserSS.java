package com.turmaa.helpdesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.turmaa.helpdesk.domains.enums.Perfil;

/**
 * Classe que implementa a interface UserDetails do Spring Security.
 * Ela serve como um "adaptador" entre a entidade Pessoa (do nosso domínio)
 * e o usuário que o Spring Security entende, fornecendo as credenciais
 * e permissões de forma padronizada.
 */
public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    
    /**
     * Coleção de permissões (autoridades) do usuário (ex: "ROLE_ADMIN", "ROLE_CLIENTE").
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Construtor que "traduz" os dados da nossa entidade Pessoa para o formato do Spring Security.
     * @param id O ID da Pessoa.
     * @param email O email da Pessoa (será o 'username').
     * @param senha A senha Criptografada da Pessoa.
     * @param perfis O conjunto de Enums Perfil da Pessoa.
     */
    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        super();
        this.id = id;
        this.email = email;
        this.senha = senha;
        // Converte o nosso Set<Perfil> em um Set<SimpleGrantedAuthority>
        // O Spring Security usa a descrição (ex: "ROLE_ADMIN") para checar as permissões.
        this.authorities = perfis.stream()
                                 .map(x -> new SimpleGrantedAuthority(x.getDescricao()))
                                 .collect(Collectors.toSet());
    }

    public Integer getId() {
        return id;
    }

    /**
     * Retorna as permissões (perfis) do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Retorna a senha criptografada do usuário.
     */
    @Override
    public String getPassword() {
        return senha;
    }

    /**
     * Retorna o nome de usuário (para o Spring Security, usamos o email).
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica se a conta do usuário não expirou.
     * (Para este projeto, está sempre ativa).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se a conta do usuário não está bloqueada.
     * (Para este projeto, está sempre ativa).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica se as credenciais (senha) do usuário não expiraram.
     * (Para este projeto, estão sempre ativas).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se o usuário está habilitado.
     * (Para este projeto, está sempre ativo).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}