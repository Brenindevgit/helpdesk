package com.turmaa.helpdesk.services;

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
 * Implementação da interface UserDetailsService do Spring Security.
 * Esta classe é a ponte entre o modelo de dados da aplicação (Pessoa)
 * e o sistema de autenticação do Spring Security (UserDetails).
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Localiza um usuário no sistema pelo seu nome de usuário (que, neste caso, é o e-mail).
     * Este método é chamado pelo Spring Security durante o processo de autenticação.
     *
     * @param email O e-mail do usuário que está tentando se autenticar.
     * @return Um objeto UserDetails (nossa classe UserSS) com os dados do usuário.
     * @throws UsernameNotFoundException Se nenhum usuário for encontrado com o e-mail fornecido.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca a pessoa pelo e-mail no repositório.
        Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        
        // Se a pessoa for encontrada, retorna uma nova instância de UserSS.
        // UserSS é a representação do nosso usuário que o Spring Security entende.
        if (pessoa.isPresent()) {
            return new UserSS(
                    pessoa.get().getId(),
                    pessoa.get().getEmail(),
                    pessoa.get().getSenha(),
                    pessoa.get().getPerfis()
            );
        }

        // Se a pessoa não for encontrada, lança a exceção padrão do Spring Security.
        throw new UsernameNotFoundException(email);
    }
}