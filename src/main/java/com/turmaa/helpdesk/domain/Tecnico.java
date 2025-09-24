package com.turmaa.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore; // 1. IMPORT RECOMENDADO
import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.domains.enums.Perfil;

/**
 * Entidade que representa um Técnico no sistema.
 * Herda os atributos comuns da classe Pessoa.
 */
@Entity
public class Tecnico extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista de chamados associados a este técnico.
	 * @OneToMany define um relacionamento de "um para muitos" (um técnico para muitos chamados).
	 * 'mappedBy = "tecnico"' indica que a entidade Chamado é a dona do relacionamento,
	 * ou seja, a tabela de Chamado terá a chave estrangeira 'tecnico_id'.
	 * * 2. SUGESTÃO: A anotação @JsonIgnore é importante aqui para evitar um loop infinito
	 * quando a API for serializar um Técnico para JSON. Sem ela, o Técnico tentaria
	 * mostrar seus chamados, e cada chamado tentaria mostrar seu técnico, criando um loop.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "tecnico")
	private List<Chamado> chamados = new ArrayList<>();
	
	/**
	 * Construtor padrão.
	 * Garante que todo novo técnico receba o perfil de TECNICO.
	 */
	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO);
	}

	/**
	 * Construtor com parâmetros, herdando da classe Pessoa.
	 */
	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO);
	}

	/**
	 * Construtor que converte um objeto TecnicoDTO para uma entidade Tecnico.
	 * Facilita a criação e atualização de técnicos a partir de dados vindos da API.
	 * @param obj O objeto TecnicoDTO contendo os dados do técnico.
	 */
	public Tecnico(TecnicoDTO obj) {
	    super(obj.getId(), obj.getNome(), obj.getCpf(), obj.getEmail(), obj.getSenha());
	    addPerfil(Perfil.TECNICO);
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
}