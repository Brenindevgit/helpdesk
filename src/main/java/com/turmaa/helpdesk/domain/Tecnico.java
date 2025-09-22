package com.turmaa.helpdesk.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.turmaa.helpdesk.domain.dtos.TecnicoDTO;
import com.turmaa.helpdesk.domains.enums.Perfil;

@Entity
public class Tecnico extends Pessoa{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "tecnico")
	private List<Chamado> chamados = new ArrayList<>();
	
	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO);
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO);
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

	/**
	 * Construtor que recebe um Data Transfer Object (DTO).
	 * Facilita a conversão dos dados do DTO para a Entidade,
	 * sendo utilizado nos serviços de criação e atualização.
	 * @param obj O objeto TecnicoDTO contendo os dados do técnico.
	 */
	public Tecnico(TecnicoDTO obj) {
	    // Chama o construtor da classe pai (Pessoa) para preencher os dados herdados.
	    super(obj.getId(), obj.getNome(), obj.getCpf(), obj.getEmail(), obj.getSenha());
	    // Garante que o perfil de TÉCNICO seja adicionado.
	    addPerfil(Perfil.TECNICO);
	}
	
}