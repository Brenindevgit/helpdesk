package com.turmaa.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore; // 1. IMPORT ADICIONADO
import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.domains.enums.Perfil;

/**
 * Entidade que representa um Cliente no sistema.
 * Herda os atributos comuns da classe abstrata Pessoa.
 */
@Entity
public class Cliente extends Pessoa {
    private static final long serialVersionUID = 1L;

    /**
     * Lista de chamados associados a este cliente.
     * @OneToMany define um relacionamento de "um para muitos".
     * 'mappedBy = "cliente"' informa ao JPA que a entidade Chamado é a dona do relacionamento.
     * @JsonIgnore é crucial para evitar loops de serialização ao converter para JSON.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    /**
     * Construtor padrão.
     * Garante que todo novo cliente receba o perfil de CLIENTE.
     */
    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    /**
     * Construtor com parâmetros, herdando da classe Pessoa.
     */
    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }
    
    /**
     * Construtor que converte um objeto ClienteDTO para uma entidade Cliente.
     * @param obj O objeto ClienteDTO contendo os dados do cliente.
     */
    public Cliente(ClienteDTO obj) {
        super(obj.getId(), obj.getNome(), obj.getCpf(), obj.getEmail(), obj.getSenha());
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}