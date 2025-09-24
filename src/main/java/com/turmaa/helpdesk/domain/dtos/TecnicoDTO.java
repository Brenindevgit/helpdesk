package com.turmaa.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty; 
import com.turmaa.helpdesk.domain.Tecnico;
import com.turmaa.helpdesk.domains.enums.Perfil; 

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet; 
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank; // Usar @NotBlank

/**
 * Data Transfer Object (DTO) para a entidade Tecnico.
 * Esta classe define a estrutura de dados que será usada para criar,
 * atualizar e visualizar Técnicos através da API, separando a representação
 * da API da representação do banco de dados (Entidade).
 */
public class TecnicoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    
    @NotBlank(message = "O campo NOME é requerido") // SUGESTÃO: @NotBlank é mais robusto que @NotEmpty
    protected String nome;
    
    @NotBlank(message = "O campo CPF é requerido")
    protected String cpf;
    
    @NotBlank(message = "O campo EMAIL é requerido")
    protected String email;
    
    /**
     * 
     * A anotação @JsonProperty com acesso WRITE_ONLY é crucial.
     * Ela permite que a senha seja recebida pela API (escrita), mas impede
     * que ela seja enviada de volta em qualquer resposta JSON (leitura),
     * evitando a exposição da senha do usuário.
     */
    @NotBlank(message = "O campo SENHA é requerido")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String senha;
    
    protected Set<Integer> perfis = new HashSet<>(); 

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO() {
        super();
        // Garante que todo DTO de técnico criado sem parâmetros já tenha o perfil de técnico.
        addPerfil(Perfil.TECNICO);
    }

    /**
     * Construtor que mapeia uma entidade Tecnico para um TecnicoDTO.
     * @param obj A entidade Tecnico a ser convertida.
     */
    public TecnicoDTO(Tecnico obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
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

	public Set<Integer> getPerfis() {
		return perfis;
	}
    
    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}