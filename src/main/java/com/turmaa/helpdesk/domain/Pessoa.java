package com.turmaa.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.turmaa.helpdesk.domains.enums.Perfil;

/**
 * Classe abstrata que serve como base para as entidades Cliente e Tecnico.
 * Centraliza os atributos e comportamentos comuns a todas as pessoas no sistema.
 * A anotação @Entity informa ao JPA que esta classe é uma tabela no banco de dados.
 */
@Entity
public abstract class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único da pessoa.
	 * @Id indica que é a chave primária.
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) configura o auto incremento no banco.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	
	protected String nome;
	
	/**
	 * CPF da pessoa.
	 * @Column(unique = true) garante que não haverá dois CPFs iguais no banco.
	 */
	@Column(unique = true)
	protected String cpf;
	
	/**
	 * E-mail da pessoa, usado para login.
	 * @Column(unique = true) garante que não haverá dois e-mails iguais no banco.
	 */
	@Column(unique = true)
	protected String email;
	
	/**
	 * Senha do usuário. Será armazenada de forma criptografada.
	 */
	protected String senha;
	
	/**
	 * Conjunto de perfis (roles) do usuário.
	 * @ElementCollection indica que os perfis serão armazenados em uma tabela separada (PERFIS).
	 * FetchType.EAGER faz com que os perfis sejam sempre carregados junto com a Pessoa.
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	protected Set<Integer> perfis = new HashSet<>();
	
	/**
	 * Data em que o cadastro da pessoa foi criado.
	 * @JsonFormat formata a data no padrão dd/MM/yyyy ao ser convertida para JSON.
	 */
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();
	
	/**
	 * Construtor padrão.
	 * Adiciona o perfil de CLIENTE por padrão a qualquer pessoa criada.
	 */
	public Pessoa() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	/**
	 * Construtor com parâmetros.
	 */
	public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Retorna os perfis do usuário no formato Enum.
	 * Realiza a conversão dos códigos (Integer) para os valores do Enum Perfil.
	 */
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	/**
	 * Adiciona um novo perfil ao usuário.
	 * O perfil é armazenado como seu código numérico.
	 */
	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Compara objetos com base nos campos 'id' e 'cpf'.
	 * Essencial para o JPA e para o funcionamento de coleções (Sets, Lists).
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}