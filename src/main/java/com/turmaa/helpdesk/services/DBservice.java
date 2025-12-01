package com.turmaa.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turmaa.helpdesk.domain.Chamado;
import com.turmaa.helpdesk.domain.Cliente;
import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domains.enums.Perfil;
import com.turmaa.helpdesk.domains.enums.Prioridade;
import com.turmaa.helpdesk.domains.enums.Status;
import com.turmaa.helpdesk.repositories.ChamadoRepository;
import com.turmaa.helpdesk.repositories.ClienteRepository;
import com.turmaa.helpdesk.repositories.TecnicoRepository;

/**
 * Serviço responsável por popular o banco de dados com dados de teste
 * quando a aplicação é iniciada em um perfil de desenvolvimento ('dev').
 */
@Service
public class DBservice {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Cria instâncias de Tecnico, Cliente e Chamado e as salva no banco de dados.
     */
    public void instanciaDB() {

        // 1. USUÁRIO ADMIN (Bill Gates)
        // Terá acesso total (Técnicos, Clientes e Chamados)
        Tecnico tec1 = new Tecnico(null, "Bill Gates", "70645777093", "bill@mail.com", encoder.encode("123"));
        tec1.addPerfil(Perfil.ADMIN);

        // 2. USUÁRIO TÉCNICO (Steve Jobs) - NOVO!
        // Terá acesso restrito (Vê Clientes e Chamados, mas NÃO vê o menu Técnicos)
        // Usei um CPF válido gerado aleatoriamente para passar na validação
        Tecnico tec2 = new Tecnico(null, "Steve Jobs", "25379685006", "steve@mail.com", encoder.encode("123"));
        tec2.addPerfil(Perfil.TECNICO);

        // 3. USUÁRIO CLIENTE (Linus Torvalds)
        // Terá acesso mínimo (Vê apenas Chamados)
        Cliente cli1 = new Cliente(null, "Linus Torvalds", "70511744613", "linus@mail.com", encoder.encode("123"));

        // Cria um chamado inicial
        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        // Salva as listas no banco
        tecnicoRepository.saveAll(Arrays.asList(tec1, tec2)); // <--- Não esqueça de incluir tec2 aqui!
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }
}