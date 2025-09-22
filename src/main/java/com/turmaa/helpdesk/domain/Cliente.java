package com.turmaa.helpdesk.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.turmaa.helpdesk.domain.dtos.ClienteDTO;
import com.turmaa.helpdesk.domains.enums.Perfil;

@Entity
public class Cliente extends Pessoa {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
    /**
     * Construtor que recebe um Data Transfer Object (DTO).
     * Este método é uma forma limpa de converter os dados que vêm da requisição (DTO)
     * para um objeto da entidade (Cliente), que será salvo no banco de dados.
     * É utilizado pelos métodos 'create' e 'update' da camada de Serviço (ClienteService).
     *
     * @param obj O objeto ClienteDTO contendo os dados do cliente.
     */
    public Cliente(ClienteDTO obj) {
        // A palavra 'super' chama o construtor da classe pai (Pessoa).
        // Estamos passando os dados do DTO para o construtor da Pessoa,
        // que já sabe como atribuir id, nome, cpf, email e senha.
        // Isso evita a repetição de código.
        super(obj.getId(), obj.getNome(), obj.getCpf(), obj.getEmail(), obj.getSenha());

        // Adiciona o perfil de CLIENTE por padrão a qualquer objeto criado
        // ou atualizado através deste construtor, garantindo a regra de negócio.
        addPerfil(Perfil.CLIENTE);
    }
}