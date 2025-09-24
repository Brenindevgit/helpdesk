package com.turmaa.helpdesk.services;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 1. IMPORT ADICIONADO
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
    
    // 2. ALTERAÇÃO: Injetando o encoder de senhas.
    // É crucial para garantir que os dados de teste também tenham senhas criptografadas.
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Cria instâncias de Tecnico, Cliente e Chamado e as salva no banco de dados.
     */
    public void instanciaDB() {

        // 3. ALTERAÇÃO: A senha agora é criptografada com encoder.encode()
        Tecnico tec1 = new Tecnico(null, "Bill Gates", "70645777093", "bill@mail.com", encoder.encode("123"));
        tec1.addPerfil(Perfil.ADMIN);

        // 3. ALTERAÇÃO: A senha agora é criptografada
        Cliente cli1 = new Cliente(null, "Linus Torvalds", "70511744613", "linus@mail.com", encoder.encode("123"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }
}