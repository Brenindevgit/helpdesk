package com.turmaa.helpdesk.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domains.enums.Perfil;
import com.turmaa.helpdesk.repositories.TecnicoRepository;
import com.turmaa.helpdesk.services.DBservice;

/**
 * Classe de configuração específica para o perfil de desenvolvimento ('dev').
 * Responsável por popular o banco de dados com dados iniciais para facilitar os testes.
 */
@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBservice dbService;

    
    // Pedi ao Spring para me dar o repositório de técnico e o encoder de senhas.
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Este método é executado automaticamente quando a aplicação sobe no perfil 'dev'.
     * Ele cria um usuário ADMIN se nenhum existir e pode chamar outros serviços para popular o banco.
     */
    @Bean
    public void instanciaDB() {
        // Lógica para criar o usuário admin.
        // Verifico no banco se o e-mail 'admin@helpdesk.com' já existe.
        // Faço isso para não criar o mesmo admin toda vez que a aplicação reiniciar.
        if (tecnicoRepository.findByEmail("admin@helpdesk.com").isEmpty()) {
            // Se não existir, eu crio um novo técnico.
            Tecnico admin = new Tecnico();
            admin.setNome("Administrador do Sistema");
            admin.setCpf("000.000.000-00");
            admin.setEmail("admin@helpdesk.com");
            // Criptografo a senha 'admin' antes de salvar.
            admin.setSenha(encoder.encode("admin"));
            // Atribuo os perfis de ADMIN e TECNICO a ele.
            admin.addPerfil(Perfil.ADMIN);
            admin.addPerfil(Perfil.TECNICO);
            
            // Salvo o novo admin no banco de dados.
            tecnicoRepository.save(admin);
        }

        
           this.dbService.instanciaDB();
    }
}