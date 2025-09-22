package com.turmaa.helpdesk.services; // Pacote de serviços

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Pessoa;
import com.turmaa.helpdesk.repositories.PessoaRepository;
import com.turmaa.helpdesk.security.UserSS;

/**
 * Implementação de UserDetailsService para autenticação de usuários.
 * O Spring Security utiliza esta classe para buscar um usuário no banco de dados
 * a partir do e-mail informado no login.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Carrega o usuário a partir do e-mail informado no login.
     * @param email O e-mail do usuário.
     * @return Uma instância de UserDetails (UserSS) com os dados do usuário.
     * @throws UsernameNotFoundException Se não houver usuário com o e-mail informado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca a pessoa pelo e-mail no repositório.
        Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        
        // Se a pessoa existir, retorna um UserSS com os dados para o Spring Security.
        if (pessoa.isPresent()) {
            return new UserSS(
                    pessoa.get().getId(),
                    pessoa.get().getEmail(),
                    pessoa.get().getSenha(),
                    pessoa.get().getPerfis()
            );
        }

        // Caso não encontre, lança a exceção padrão do Spring Security.
        throw new UsernameNotFoundException(email);
    }
}